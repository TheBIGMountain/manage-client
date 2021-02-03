package pages.admin.table

import ajax.table.*
import antd.card.card
import antd.table.ColumnProps
import antd.table.TableComponent
import antd.table.table
import kotlinext.js.js
import kotlinext.js.jsObject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.css.px
import react.*
import util.withKey

private val highTableHeader = arrayOf<ColumnProps<HighTable>>(
  jsObject { title = "id"; key = "id"; dataIndex = "id"; fixed = "left"; width = 100 },
  jsObject { title = "用户名"; dataIndex = "username"; key = "username"; fixed = "left"; width = 100 },
  jsObject { title = "年龄"; dataIndex = "age"; key = "age"; width = 140 },
  jsObject { title = "性别"; dataIndex = "gender"; key = "gender"; width = 140
    render = { gender, _, _ -> if (gender == 1) "男" else "女" }
  },
  jsObject { title = "状态"; dataIndex = "status"; key = "status"; width = 140
    render = { status, _, _ -> when(status) {
      1 -> "咸鱼一条"; 2 -> "风华浪子"; 3 -> "北大才子"; 4 -> "百度FE"; else -> "创业者"
    } }
  },
  jsObject { title = "爱好"; dataIndex = "hobby"; key = "hobby"; width = 140
    render = { hobby, _, _ -> when(hobby) {
      1 -> "游泳"; 2 -> "打篮球"; 3 -> "踢足球"; 4 -> "爬山"; 5 -> "桌球"; 6 -> "骑行"; 7 -> "麦霸"
      else -> "跑步"
    } }
  },
  jsObject { title = "生日"; dataIndex = "birthday"; key = "birthday"; width = 140 },
  jsObject { title = "地址"; dataIndex = "address"; key = "address"; width = 140 },
  jsObject { title = "早起时间"; dataIndex = "time"; key = "time"; width = 140 },
  jsObject { title = "是否已婚"; dataIndex = "isMarried1"; key = "isMarried1"; width = 140
    render = { b, _, _ -> if (b == 1) "已婚" else "未婚" }
  },
  jsObject { title = "是否已婚"; dataIndex = "isMarried2"; key = "isMarried2"; width = 140
    render = { b, _, _ -> if (b == 1) "已婚" else "未婚" }
  },
  jsObject { title = "是否已婚"; dataIndex = "isMarried3"; key = "isMarried3"; width = 140
    render = { b, _, _ -> if (b == 1) "已婚" else "未婚" }
  },
  jsObject { title = "是否已婚"; dataIndex = "isMarried4"; key = "isMarried4"; width = 140
    render = { b, _, _ -> if (b == 1) "已婚" else "未婚" }
  },
  jsObject { title = "是否已婚"; dataIndex = "isMarried5"; key = "isMarried5"; width = 140
    render = { b, _, _ -> if (b == 1) "已婚" else "未婚" }
  },
  jsObject { title = "是否已婚"; dataIndex = "isMarried6"; key = "isMarried6"; width = 140
    render = { b, _, _ -> if (b == 1) "已婚" else "未婚" }
  },
  jsObject { title = "是否已婚"; dataIndex = "isMarried7"; key = "isMarried7"; width = 140
    render = { b, _, _ -> if (b == 1) "已婚" else "未婚" }
  },
  jsObject { title = "是否已婚"; dataIndex = "isMarried8"; key = "isMarried8"; width = 140
    render = { b, _, _ -> if (b == 1) "已婚" else "未婚" }
  },
)

private fun init() = MainScope().launch {
  val current = 1
  launch { current.tableList().withKey().onEach { setState(state.copy(list = it.list)) }.collect() }
  launch { current.tableList1().withKey().onEach { setState(state.copy(list1 = it.list)) }.collect() }
  launch { current.tableHighList().withKey().onEach { setState(state.copy(highList = it.list)) }.collect() }
}

private lateinit var state: HighTableState
private lateinit var setState: (HighTableState) -> Unit

private data class HighTableState(
  val list: Array<BasicTable> = emptyArray(),
  val list1: Array<BasicTable> = emptyArray(),
  val highList: Array<HighTable> = emptyArray()
)

private val highTable = functionalComponent<RProps> {
  useState(HighTableState()).run { state = first; setState = second }

  useEffect(emptyList()) { init() }

  card { attrs { title = "头部固定" }
    table<BasicTable, TableComponent<BasicTable>> {
      attrs {
        scroll = jsObject { y = 240 }
        columns = tableColumns
        dataSource = state.list1
        bordered = true
        pagination = false
      }
    }
  }
  card { attrs { title = "左侧固定"; style = js { marginTop = 10.px } }
    table<HighTable, TableComponent<HighTable>> {
      attrs {
        scroll = jsObject { x = 2300 }
        columns = highTableHeader
        dataSource = state.highList
        bordered = true
        pagination = false
      }
    }
  }
  card { attrs { title = "操作按钮"; style = js { marginTop = 10.px } }
    table<BasicTable, TableComponent<BasicTable>> {
      attrs {
        columns = badgeColumn
        dataSource = state.list
        bordered = true
        pagination = false
      }
    }
  }
}

fun RBuilder.highTable() = child(highTable)


