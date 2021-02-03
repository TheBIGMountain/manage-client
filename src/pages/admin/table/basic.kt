package pages.admin.table

import ajax.table.BasicTable
import ajax.table.tableList
import antd.button.button
import antd.card.card
import antd.message.message
import antd.modal.ModalComponent
import antd.pagination.PaginationConfig
import antd.table.TableComponent
import antd.table.table
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
import kotlin.js.Promise

private val mockData = arrayOf<BasicTable>(
  jsObject {
    id = 1; username = "张三"; age = 18; gender = 1; status = 1; hobby = 1
    birthday = "2000-01-01"; address = "黑龙江大庆石油学院"; time = "09:00"
  },
  jsObject {
    id = 2; username = "李四"; age = 18; gender = 2; status = 2; hobby = 2
    birthday = "2000-01-01"; address = "黑龙江大庆石油学院"; time = "09:00"
  },
  jsObject {
    id = 3; username = "王五"; age = 18; gender = 3; status = 3; hobby = 3
    birthday = "2000-01-01"; address = "黑龙江大庆石油学院"; time = "09:00"
  }
)

private fun RBuilder.commonTable(list: Array<BasicTable>) {
  table<BasicTable, TableComponent<BasicTable>> {
    attrs {
      columns = tableColumns
      dataSource = list
      bordered = true
      pagination = false
    }
  }
}

private lateinit var state: BasicTableState
private lateinit var setState: (BasicTableState) -> Unit

private data class BasicTableState(
  var current: Int = 1,
  val list: Array<BasicTable> = emptyArray(),
  val selectedRowKey: String = "0",
  val selectedRowKeys: Array<String> = emptyArray(),
  val selectIds: Array<String> = emptyArray(),
  val selectedItem: BasicTable = jsObject {  },
  val pagination: PaginationConfig = jsObject {  }
)

private fun handleDelete() = state.selectIds.let {
  if (it.isNotEmpty()) {
    ModalComponent.confirm(jsObject {
      title = "删除提示"
      content = "您确定要删除这些数据吗?\t${it.joinToString(",")}"
      onOk = {
        MainScope().launch {
          delay(1000)
          setState(state.copy(selectedRowKeys = emptyArray(), selectIds = emptyArray()))
          message.success("删除成功")
          ModalComponent.destroyAll()
          getTableList()
        }
        Promise<dynamic> { _, _ -> }
      }
    })
  }
}

private fun getTableList(): Job = MainScope().launch {
  state.current.tableList()
    .withKey()
    .onEach { page ->
      page.toPagination {
        state.current = it.toInt()
        getTableList()
      }.let { setState(state.copy(list = page.list, pagination = it)) }
    }.collect()
}

private val basicTable = functionalComponent<RProps> {
  useState(BasicTableState()).run { state = first; setState = second }

  useEffect(emptyList()) { getTableList() }

  card { attrs { title = "基础表格" }
    mockData.withIndex().forEach { it.value.asDynamic().key = it.index }
    commonTable(mockData)
  }
  card { attrs { title = "动态表格"; style = js { marginTop = 10.px } }
    commonTable(state.list)
  }
  card { attrs { title = "动态单选"; style = js { marginTop = 10.px } }
    table<BasicTable, TableComponent<BasicTable>> {
      attrs {
        rowSelection = jsObject { type = "radio"; selectedRowKeys = state.selectedRowKey }
        columns = tableColumns
        dataSource = state.list
        bordered = true
        pagination = false
        onRow = { record, i -> jsObject {
          onClick = {
            setState(state.copy(selectedItem = record, selectedRowKey = "${i.toInt()}"))
            ModalComponent.info(jsObject {
              title = "信息"
              content = "用户名: ${record.username}, 用户生日: ${record.birthday}"
            })
          }
        } }
      }
    }
  }
  card { attrs { title = "动态多选"; style = js { marginTop = 10.px } }
    button {
      attrs {
        style = js { display = "block"; marginBottom = 10.px }
        onClickCapture = { handleDelete() }
      }; +"删除"
    }
    table<BasicTable, TableComponent<BasicTable>> {
      attrs {
        columns = tableColumns
        dataSource = state.list
        bordered = true
        pagination = false
        rowSelection = jsObject {
          type = "checkbox"
          selectedRowKeys = state.selectedRowKeys
          onChange = { rowKeys, rows ->
            setState(state.copy(
              selectedRowKeys = rowKeys.unsafeCast<Array<String>>(),
              selectIds = rows.map { "${it.id}" }.toTypedArray()
            ))
          }
      } }
    }
  }
  card { attrs { title = "表格分页"; style = js { marginTop = 10.px } }
    table<BasicTable, TableComponent<BasicTable>> {
      attrs {
        columns = tableColumns
        dataSource = state.list
        bordered = true
        pagination = state.pagination
      }
    }
  }
}

fun RBuilder.basicTable() = child(basicTable)