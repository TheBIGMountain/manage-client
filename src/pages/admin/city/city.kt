package pages.admin.city

import ajax.city.CityInfo
import ajax.city.UserInfo
import ajax.city.openCities
import ajax.city.openCity
import antd.button.button
import antd.card.card
import antd.form.FormItemProps
import antd.form.form
import antd.form.formItem
import antd.message.message
import antd.modal.modal
import antd.pagination.PaginationConfig
import antd.select.SelectComponent
import antd.select.option
import antd.select.select
import antd.table.ColumnProps
import antd.table.TableComponent
import antd.table.table
import components.basic_form.FormItemData
import components.basic_form.filterForm
import kotlinext.js.js
import kotlinext.js.jsObject
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.css.px
import react.*
import util.toPagination
import util.withKey

private val cityFormItemLayout = jsObject<FormItemProps> {
  labelCol = jsObject { span = 5 }
  wrapperCol = jsObject { span = 19 }
}

private val cityTableColumns = arrayOf<ColumnProps<dynamic>>(
  jsObject { title = "城市ID"; dataIndex = "id" },
  jsObject { title = "城市名称"; dataIndex = "name" },
  jsObject { title = "用车模式"; dataIndex = "mode"; render = { item, _, _ ->
    if (item.unsafeCast<Int>() == 1) "停车点" else "禁停区"
  }},
  jsObject { title = "营运模式"; dataIndex = "opMode"; render = { item, _, _ ->
    if (item.unsafeCast<Int>() == 1) "自营" else "加盟"
  }},
  jsObject { title = "授权加盟商"; dataIndex = "franchiseeName" },
  jsObject { title = "城市管理员"; dataIndex = "cityAdmins"; render = { item, _, _ ->
    item.unsafeCast<Array<UserInfo>>().joinToString(", ") { it.userName }
  } },
  jsObject { title = "城市开通时间"; dataIndex = "openTime" },
  jsObject { title = "操作时间"; dataIndex = "updateTime" },
  jsObject { title = "操作人"; dataIndex = "sysUsername" },
)

private val cityFilterFormList = arrayOf<FormItemData> (
  jsObject { type = "SELECT"; label = "城市"; placeholder = "全部"; width = 100
    list = arrayOf(
      jsObject { id = "0"; name = "全部" },
      jsObject { id = "1"; name = "北京市" },
      jsObject { id = "2"; name = "天津市" },
      jsObject { id = "3"; name = "深圳市" },
    )
  },
  jsObject { type = "SELECT"; label = "用车模式"; placeholder = "全部"; width = 160
    list = arrayOf(
      jsObject { id = "0"; name = "全部" },
      jsObject { id = "1"; name = "指定停车点模式" },
      jsObject { id = "2"; name = "禁停区模式" },
    )
  },
  jsObject { type = "SELECT"; label = "营运模式"; placeholder = "全部"; width = 80
    list = arrayOf(
      jsObject { id = "0"; name = "全部" },
      jsObject { id = "1"; name = "加盟" },
      jsObject { id = "2"; name = "自营" },
    )
  },
  jsObject { type = "SELECT"; label = "加盟商授权状态"; placeholder = "全部"; width = 100
    list = arrayOf(
      jsObject { id = "0"; name = "全部" },
      jsObject { id = "1"; name = "已授权" },
      jsObject { id = "2"; name = "未授权" },
    )
  }
)

private lateinit var state: CityState
private lateinit var setState: (CityState) -> Unit

private data class CityState(
  var currentPage: Int = 1,
  val showOpenCity: Boolean = false,
  val cityInfos: Array<CityInfo> = emptyArray(),
  val pagination: PaginationConfig = jsObject {  }
)

private fun submit() = MainScope().launch {
  openCity().onEach {
    message.info("正在开通~")
    delay(1000)
    setState(state.copy(showOpenCity = false))
    message.success("开通成功")
  }.onEach { getCityList() }.collect()
}

private fun getCityList(): Job = MainScope().launch {
  state.currentPage.openCities()
    .withKey()
    .onEach { page ->
      page.toPagination { cur ->
        state.currentPage = cur.toInt()
        getCityList()
      }.let { setState(state.copy(cityInfos = page.list, pagination = it)) }
    }.collect()
}

private val city = functionalComponent<RProps> {
  useState(CityState()).run { state = first; setState = second }

  useEffect(emptyList()) { getCityList() }

  card {
    filterForm(cityFilterFormList) {  }
  }
  card { attrs { style = js { marginTop = 10.px } }
    button {
      attrs {
        type = "primary"
        onClick = { setState(state.copy(showOpenCity = true)) }
      }; +"开通城市"
    }
  }
  contentWrap {
    table<CityInfo, TableComponent<CityInfo>> {
      attrs {
        bordered = true
        columns = cityTableColumns
        dataSource = state.cityInfos
        pagination = state.pagination
      }
    }
  }
  modal { attrs { title = "开通城市"; visible = state.showOpenCity }
    attrs {
      onCancel = { setState(state.copy(showOpenCity = false)) }
      onOk = { submit() }
    }
    form { attrs { layout = "horizontal" }
      formItem {
        attrs {
          label =  "选择城市"
          labelCol = cityFormItemLayout.labelCol
          wrapperCol = cityFormItemLayout.wrapperCol
        }
        select<String, SelectComponent<String>> {
          attrs { style = js { width = 100.px }; defaultValue = "1" }
          option { attrs.value = "0"; +"全部" }
          option { attrs.value = "1"; +"北京市" }
          option { attrs.value = "2"; +"天津市" }
        }
      }
      formItem {
        attrs {
          label =  "营运模式"
          labelCol = cityFormItemLayout.labelCol
          wrapperCol = cityFormItemLayout.wrapperCol
        }
        select<String, SelectComponent<String>> {
          attrs { style = js { width = 100.px }; defaultValue = "1" }
          option { attrs.value = "1"; +"自营" }
          option { attrs.value = "2"; +"加盟" }
        }
      }
      formItem {
        attrs {
          label =  "用车模式"
          labelCol = cityFormItemLayout.labelCol
          wrapperCol = cityFormItemLayout.wrapperCol
        }
        select<String, SelectComponent<String>> {
          attrs { style = js { width = 120.px }; defaultValue = "1" }
          option { attrs.value = "1"; +"指定停车点" }
          option { attrs.value = "2"; +"禁停区" }
        }
      }
    }
  }
}

fun RBuilder.city() = child(city)