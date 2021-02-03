package pages.admin.order

import ajax.bike.BikeInfo
import ajax.order.OrderDetail
import ajax.order.orderBikeInfo
import ajax.order.orderFinish
import ajax.order.orderList
import antd.button.button
import antd.card.card
import antd.form.FormItemProps
import antd.form.form
import antd.form.formItem
import antd.message.message
import antd.modal.ModalComponent
import antd.modal.modal
import antd.pagination.PaginationConfig
import antd.table.ColumnProps
import antd.table.TableComponent
import antd.table.table
import components.basic_form.FormItemData
import components.basic_form.filterForm
import kotlinext.js.jsObject
import kotlinx.browser.window
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.css.px
import pages.admin.city.contentWrap
import react.*
import util.toPagination
import util.withKey

private val orderTableColumns = arrayOf<ColumnProps<OrderDetail>>(
  jsObject { title = "订单编号"; dataIndex = "orderSn" },
  jsObject { title = "车辆编号"; dataIndex = "bikeSn" },
  jsObject { title = "用户名"; dataIndex = "userName" },
  jsObject { title = "手机号码"; dataIndex = "mobile" },
  jsObject { title = "里程"; dataIndex = "distance"; render = { distance, _, _ -> "${distance.toString().toInt() / 1000}km" } },
  jsObject { title = "行驶时长"; dataIndex = "totalTime" },
  jsObject { title = "状态"; dataIndex = "status"; render = { item, _, _ ->
    when(item) {
      1 -> "咸鱼一条"; 2 -> "风华浪子"; 3 -> "北大才子"; 4 -> "百度FE"; else -> "创业者"
    }
  } },
  jsObject { title = "开始时间"; dataIndex = "startTime"; width = 200; },
  jsObject { title = "结束时间"; dataIndex = "endTime"; width = 200; },
  jsObject { title = "订单金额"; dataIndex = "totalFee" },
  jsObject { title = "实付金额"; dataIndex = "userPay" },
)

private val filterFormList = arrayOf<FormItemData>(
  jsObject {
    type = "SELECT"; label = "城市"; placeholder = "全部"; defaultValue = "0"; width = 100
    list = arrayOf(
      jsObject { id = "0"; name = "全部" },
      jsObject { id = "1"; name = "北京" },
      jsObject { id = "2"; name = "天津" },
      jsObject { id = "3"; name = "上海" },
    )
  },
  jsObject {
    type = "SELECT"; label = "订单状态"; placeholder = "全部"; defaultValue = "1"; width = 100
    list = arrayOf(
      jsObject { id = "0"; name = "全部" },
      jsObject { id = "1"; name = "进行中" },
      jsObject { id = "2"; name = "结束行程" },
    )
  },
  jsObject { type = "TIME" },
)

private val orderFormItemLayout = jsObject<FormItemProps> {
  labelCol = jsObject { span = 5 }
  wrapperCol = jsObject { span = 19 }
}

private fun RBuilder.formItem(props: FormItemProps.() -> Unit,
                              content: RBuilder.() -> Unit)
= formItem {
  attrs {
    labelCol = orderFormItemLayout.labelCol
    wrapperCol = orderFormItemLayout.wrapperCol
    props()
  }
  content()
}

private fun isSelect(func: () -> Unit) {
  if (state.selectItem.id == undefined)
    ModalComponent.info(jsObject {
      title = "信息"
      content = "请选择一条订单"
    })
  else func()
}

private fun lookOrderDetail() = isSelect {
  window.open("/#/common/order/detail/${state.selectItem.id}", "_blank")
}

private fun orderConfirm() = isSelect {
  MainScope().launch {
    orderBikeInfo().onEach {
      setState(state.copy(showConfirm = true, bikeInfo = it))
    }.collect()
  }
}

private fun getOrderList(): Job = MainScope().launch {
  state.current.orderList()
    .withKey()
    .onEach { page ->
      page.toPagination {
        state.current = it.toInt()
        getOrderList()
      }.let {
        setState(state.copy(
          list = page.list,
          pagination = it,
          selectRowKey = "0",
          selectItem = page.list[0]
        ))
      }
    }.collect()
}

private fun finish() = MainScope().launch {
  orderFinish().onEach {
    message.info("正在结束订单~")
    delay(1000)
    setState(state.copy(showConfirm = false))
  }.onEach { message.success("订单结束成功")
  }.onEach { getOrderList() }.collect()
}

private lateinit var state: OrderState
private lateinit var setState: (OrderState) -> Unit

private data class OrderState(
  var current: Int = 1,
  val selectItem: OrderDetail = jsObject {  },
  val showConfirm: Boolean = false,
  val bikeInfo: BikeInfo = jsObject {  },
  val list: Array<OrderDetail> = emptyArray(),
  val pagination: PaginationConfig = jsObject {  },
  val selectRowKey: String = "0"
)

private val order = functionalComponent<RProps> {
  useState(OrderState()).run { state = first; setState = second }

  useEffect(emptyList()) { getOrderList() }

  card {
    filterForm(filterFormList) {  }
  }
  card { attrs { style = jsObject { marginTop = 10.px } }
    button {
      attrs {
        type = "primary"
        onClickCapture = { lookOrderDetail() }
      }; +"订单详情"
    }
    button {
      attrs {
        type = "primary"
        style = jsObject { marginLeft = 20.px }
        onClickCapture = { orderConfirm() }
      }; +"结束订单"
    }
  }
  contentWrap {
    table<OrderDetail, TableComponent<OrderDetail>> {
      attrs {
        scroll = jsObject { x = 1500 }
        rowSelection = jsObject { type = "radio"; selectedRowKeys = state.selectRowKey }
        bordered = true
        columns = orderTableColumns
        dataSource = state.list
        pagination = state.pagination
        onRow = { record, i -> jsObject {
          onClick = { setState(state.copy(selectItem = record, selectRowKey = "${i.toInt()}")) }
        } }
      }
    }
  }
  modal {
    attrs {
      title = "结束订单"
      visible = state.showConfirm
      width = 600
      onCancel = { setState(state.copy(showConfirm = false)) }
      onOk = { finish() }
    }
    form {  attrs { layout = "horizontal" }
      state.bikeInfo.let {
        formItem({ label = "车辆编号" }) { +it.bikeSn }
        formItem({ label = "剩余电量" }) { +"${if (it.battery == undefined) 0 else it.battery}%" }
        formItem({ label = "行程开始时间" }) { +it.startTime }
        formItem({ label = "当前位置" }) { +it.location }
      }
    }
  }
}

fun RBuilder.order() = child(order)