package pages.common.order_detail

import ajax.order.OrderDetail
import ajax.order.orderDetail
import antd.card.card
import kotlinext.js.jsObject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import react.*
import react.dom.li

private fun init(setOrderDetail: (OrderDetail) -> Unit) = MainScope().launch {
  orderDetail()
    .onEach { setOrderDetail(it) }
    .onEach { it.renderMap() }
    .collect()
}

private val orderDetail = functionalComponent<RProps> {
  val (orderDetail, setOrderDetail) = useState<OrderDetail>(jsObject { })

  useEffect(emptyList()) { init(setOrderDetail) }

  card {
    orderMap()
    detailItem {
      itemTitle { +"基础信息" }
      detailForm {
        li {
          detailFormLeft { +"用车模式" }
          detailFormContent {
            if (orderDetail.mode != undefined)
              +if (orderDetail.mode == 1) "服务区" else "停车点"
          }
        }
        li {
          detailFormLeft { +"订单编号" }
          detailFormContent { +orderDetail.orderSn }
        }
        li {
          detailFormLeft { +"车辆编号" }
          detailFormContent { +orderDetail.bikeSn }
        }
        li {
          detailFormLeft { +"用户姓名" }
          detailFormContent { +orderDetail.username }
        }
        li {
          detailFormLeft { +"手机号码" }
          detailFormContent { +orderDetail.mobile }
        }
      }
    }
    detailItem {
      itemTitle { +"行驶轨迹" }
      detailForm {
        li {
          detailFormLeft { +"行程起点" }
          detailFormContent { +orderDetail.startLocation }
        }
        li {
          detailFormLeft { +"行驶终点" }
          detailFormContent { +orderDetail.endLocation }
        }
        li {
          detailFormLeft { +"行驶旅程" }
          detailFormContent {
            +"${(orderDetail.distance.let { if (it == undefined) 0 else it }) / 1000}公里"
          }
        }
      }
    }
  }
}

fun RBuilder.orderDetail() = child(orderDetail)