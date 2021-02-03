package pages.admin.table

import ajax.table.BasicTable
import antd.badge.badge
import antd.button.button
import antd.message.message
import antd.modal.ModalComponent
import antd.table.ColumnProps
import kotlinext.js.jsObject
import react.buildElement

/**
 * 基础表头
 */
val tableColumns = getTableColumns().toMutableList().onEach { it.width = 100 }.toTypedArray()

/**
 * 带操作元素数据表头
 */
val badgeColumn = getTableColumns().toMutableList().apply {
  repeat(5) { get(it).width = 120 }
  set(5, jsObject { title = "徽标"; dataIndex = "hobby"; key = "hobby"; width = 120
    render = { hobby, _, _ ->
      when (hobby) {
        1 -> buildElement { badge { attrs { status = "success"; text = "成功" } } }
        2 -> buildElement { badge { attrs { status = "error"; text = "报错" } } }
        3 -> buildElement { badge { attrs { status = "default"; text = "正常" } } }
        4 -> buildElement { badge { attrs { status = "processing"; text = "进行中" } } }
        else -> buildElement { badge { attrs { status = "warning"; text = "警告" } } }
      }.unsafeCast<Any>()
    }
  })
  get(6).width = 200
  get(7).width = 200
  set(8, jsObject {
    title = "操作"
    render = { _, _, _ ->
      buildElement {
        button {  attrs { size = "small"; onClickCapture = {
          ModalComponent.confirm(jsObject {
            title = "确认"
            content = "您确认要删除此条数据吗?"
            onOk = { message.success("删除成功") }
          })
        } } ;+"删除" }
      }.unsafeCast<Any>()
    }
  })
}.toTypedArray()

private fun getTableColumns(): Array<ColumnProps<BasicTable>> {
  return arrayOf(
    jsObject { title = "id"; key = "id"; dataIndex = "id" },
    jsObject { title = "用户名"; dataIndex = "username"; key = "username" },
    jsObject { title = "年龄"; dataIndex = "age"; key = "age" },
    jsObject { title = "性别"; dataIndex = "gender"; key = "gender"
      render = { gender, _, _ -> if (gender == 1) "男" else "女" }
    },
    jsObject { title = "状态"; dataIndex = "status"; key = "status"
      render = { status, _, _ -> when(status) {
        1 -> "咸鱼一条"; 2 -> "风华浪子"; 3 -> "北大才子"; 4 -> "百度FE"; else -> "创业者"
      } }
    },
    jsObject { title = "爱好"; dataIndex = "hobby"; key = "hobby"
      render = { hobby, _, _ -> when(hobby) {
        1 -> "游泳"; 2 -> "打篮球"; 3 -> "踢足球"; 4 -> "爬山"; 5 -> "桌球"; 6 -> "骑行"; 7 -> "麦霸"
        else -> "跑步"
      } }
    },
    jsObject { title = "生日"; dataIndex = "birthday"; key = "birthday" },
    jsObject { title = "地址"; dataIndex = "address"; key = "address" },
    jsObject { title = "早起时间"; dataIndex = "time"; key = "time" }
  )
}

