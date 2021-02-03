package pages.admin.map

import ajax.bike.BikeList
import ajax.bike.bikeList
import antd.card.card
import components.basic_form.FormItemData
import components.basic_form.filterForm
import kotlinext.js.jsObject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.html.id
import modules.BMapGL
import react.*
import react.dom.div

private val filterFormList = arrayOf<FormItemData>(
  jsObject {
    type = "SELECT"; label = "城市"; placeholder = "全部"; defaultValue = "0"; width = 80
    list = arrayOf(
      jsObject { id = "0"; name = "全部" },
      jsObject { id = "1"; name = "北京" },
      jsObject { id = "2"; name = "上海" },
      jsObject { id = "3"; name = "天津" },
      jsObject { id = "4"; name = "杭州" },
    )
  },
  jsObject { type = "TIME"; },
  jsObject {
    type = "SELECT"; label = "订单状态"; placeholder = "全部"; defaultValue = "0"; width = 120
    list = arrayOf(
      jsObject { id = "0"; name = "全部" },
      jsObject { id = "1"; name = "进行中" },
      jsObject { id = "2"; name = "行程结束" },
    )
  },
)

// 绘制起点和终点
private fun BMapGL.Map.drawStartAndEndPoint(routeList: Array<String>) {
  // 获取起点
  val gps1 = routeList[0].split(",")
  val startPoint = BMapGL.Point(gps1[0].toDouble(), gps1[1].toDouble())
  // 获取终点
  val gps2 = routeList[routeList.size - 1].split(",")
  val endPoint = BMapGL.Point(gps2[0].toDouble(), gps2[1].toDouble())

  // 锁定地图中心
  centerAndZoom(endPoint, 11)

  // 添加起始点和终止点图标
  val startPointIcon = BMapGL.Icon("/assets/start_point.png", BMapGL.Size(36.0, 42.0), jsObject {
    imageSize = BMapGL.Size(36.0, 42.0)
    anchor = BMapGL.Size(18.0, 42.0)
  })
  val endPointIcon = BMapGL.Icon("/assets/end_point.png", BMapGL.Size(36.0, 42.0), jsObject {
    imageSize = BMapGL.Size(36.0, 42.0)
    anchor = BMapGL.Size(18.0, 42.0)
  })
  val startMarker = BMapGL.Marker(startPoint, jsObject { icon = startPointIcon })
  val endMarker = BMapGL.Marker(endPoint, jsObject { icon = endPointIcon })
  addOverlay(startMarker)
  addOverlay(endMarker)
}

// 绘制行驶路线
private fun BMapGL.Map.drawDriveRoad(routeList: Array<String>) {
  routeList.map { it.split(",") }
    .map { BMapGL.Point(it[0].toDouble(), it[1].toDouble()) }
    .toList().toTypedArray()
    .let {
      BMapGL.Polyline(it, jsObject {
        strokeColor = "#0000CC"
        strokeWeight = 2
        strokeOpacity = 1.0
      }) }
    .let { addOverlay(it) }
}

// 绘制服务区
private fun BMapGL.Map.drawServiceArea(serviceList: Array<dynamic>) {
  serviceList.map { BMapGL.Point(it.lon.toString().toDouble(), it.lat.toString().toDouble()) }
    .toList().toTypedArray()
    .let {
      BMapGL.Polygon(it, jsObject {
        strokeColor = "#ef4136"
        strokeWeight = 3
        strokeOpacity = 1.0
      }) }
    .let { addOverlay(it) }
}

// 绘制自行车图标和路线
private fun BMapGL.Map.drawBikeIconAndRoad(bikeList: Array<String>) {
  // 添加自行车图标
  val bikeIcon = BMapGL.Icon("/assets/bike.jpg", BMapGL.Size(36.0, 42.0), jsObject {
    imageSize = BMapGL.Size(36.0, 42.0)
    anchor = BMapGL.Size(18.0, 42.0)
  })

  // 获取自行车位置
  bikeList.map { it.split(",") }
    .map { BMapGL.Point(it[0].toDouble(), it[1].toDouble()) }
    .map { BMapGL.Marker(it, jsObject { icon = bikeIcon }) }
    .onEach { addOverlay(it) }
}

private fun BMapGL.Map.renderMap(bikeList: BikeList) {
  drawStartAndEndPoint(bikeList.routeList)

  drawDriveRoad(bikeList.routeList)

  drawServiceArea(bikeList.serviceList)

  drawBikeIconAndRoad(bikeList.bikeList)
}

private fun getBikeList(setCount: (Int) -> Unit) = MainScope().launch {
  bikeList().onEach { setCount(it.totalCount) }
    .onEach { BMapGL.Map("bikeMap").renderMap(it) }
    .collect()
}

private val bikeMap = functionalComponent<RProps> {
  val (count, setCount) = useState(0)

  useEffect(emptyList()) { getBikeList(setCount) }

  card {
    filterForm(filterFormList) {}
  }
  card {
    div { +"共${count}辆" }
    mapContainer { attrs.id = "bikeMap" }
  }
}

fun RBuilder.bikeMap() = child(bikeMap)