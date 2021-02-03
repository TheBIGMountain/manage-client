package pages.common.order_detail

import ajax.bike.Position
import ajax.bike.Service
import ajax.order.OrderDetail
import kotlinext.js.jsObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import modules.BMapGL

internal fun OrderDetail.renderMap() = GlobalScope.launch {
  val map = BMapGL.Map("orderDetailMap")
  map.addMapControl()
  map.drawBikeRoute(positionList)
  map.drawServiceArea(area)
}

private fun BMapGL.Map.addMapControl() {
  addControl(BMapGL.ScaleControl(jsObject { anchor = 1 }))
  addControl(BMapGL.NavigationControl(jsObject { anchor = 1 }))
}

private fun BMapGL.Map.drawBikeRoute(list: Array<Position>) = GlobalScope.launch {
  if (list.isNotEmpty()) {
    list[0].let { start ->
      val startPoint = BMapGL.Point(start.lon.toDouble(), start.lat.toDouble())
      val startIcon = BMapGL.Icon("/assets/start_point.png", BMapGL.Size(36.0, 42.0), jsObject {
        imageSize = BMapGL.Size(36.0, 42.0)
        anchor = BMapGL.Size(36.0, 42.0)
      })
      val startMarker = BMapGL.Marker(startPoint, jsObject { icon = startIcon })
      addOverlay(startMarker)
    }
    list[list.size - 1].let { end ->
      val endPoint = BMapGL.Point(end.lon.toDouble(), end.lat.toDouble())
      val endIoc = BMapGL.Icon("/assets/end_point.png", BMapGL.Size(36.0, 42.0), jsObject {
        imageSize = BMapGL.Size(36.0, 42.0)
        anchor = BMapGL.Size(36.0, 42.0)
      })
      val startMarker = BMapGL.Marker(endPoint, jsObject { icon = endIoc })
      addOverlay(startMarker)

      list.asFlow()
        .map { BMapGL.Point(it.lon.toDouble(), it.lat.toDouble()) }
        .toList().toTypedArray()
        .let {
          BMapGL.Polyline(it, jsObject {
            strokeColor = "#1869AD"
            strokeWeight = 3
            strokeOpacity = 1.0
          })
        }.let { addOverlay(it) }
      centerAndZoom(endPoint, 11)
    }
  }
}

private fun BMapGL.Map.drawServiceArea(area: Array<Service>) = GlobalScope.launch {
  area.asFlow()
    .map { BMapGL.Point(it.lon.toDouble(), it.lat.toDouble()) }
    .toList().toTypedArray()
    .let {
      BMapGL.Polygon(it, jsObject {
        strokeColor = "#CE0000"
        strokeWeight = 4
        strokeOpacity = 1.0
        fillColor = "#ff8605"
        fillOpacity = 0.4
      })
    }.let { addOverlay(it) }
}