package pages.admin.user

import ajax.user.User
import ajax.user.userAdd
import ajax.user.userDelete
import ajax.user.userList
import antd.button.button
import antd.card.card
import antd.datepicker.datePicker
import antd.form.FormItemProps
import antd.form.form
import antd.form.formItem
import antd.input.input
import antd.input.textArea
import antd.message.message
import antd.modal.ModalComponent
import antd.modal.modal
import antd.pagination.PaginationConfig
import antd.radio.radio
import antd.radio.radioGroup
import antd.select.SelectComponent
import antd.select.option
import antd.select.select
import antd.table.ColumnProps
import antd.table.TableComponent
import antd.table.table
import components.basic_form.FormItemData
import components.basic_form.filterForm
import kotlinext.js.jsObject
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.css.px
import kotlinx.html.INPUT
import pages.admin.city.contentWrap
import react.*
import util.toPagination
import util.withKey
import kotlin.js.Promise

private val filterFormList = arrayOf<FormItemData>(
  jsObject { type = "INPUT"; label = "用户名"; placeholder = "请输入用户名"; width = 100 },
  jsObject { type = "DATE"; label = "入职日期"; placeholder = "请选择入职日期" },
  jsObject { type = "INPUT"; label = "手机号"; placeholder = "请输入手机号"; width = 100 },
)

private val userTableHeader = arrayOf<ColumnProps<User>>(
  jsObject { title = "id"; dataIndex = "id" },
  jsObject { title = "用户名"; dataIndex = "username" },
  jsObject { title = "性别"; dataIndex = "gender"; render = { item, _, _ ->
    if (item.unsafeCast<Int>() == 1) "男" else "女"
  } },
  jsObject { title = "状态"; dataIndex = "status"; render = { item, _, _ ->
    when(item.unsafeCast<Int>()) {
      1 -> "咸鱼一条"; 2 -> "风华浪子"; 3 -> "北大才子"; 4 -> "百度FE"; else -> "创业者"
    }
  } },
  jsObject { title = "爱好"; dataIndex = "hobby"; render = { item, _, _ ->
    when(item.unsafeCast<Int>()) {
      1 -> "游泳"; 2 -> "打篮球"; 3 -> "踢足球"; 4 -> "爬山"; 5 -> "桌球"; 6 -> "骑行"; 7 -> "麦霸"
      else -> "跑步"
    }
  } },
  jsObject { title = "生日"; dataIndex = "birthday" },
  jsObject { title = "联系地址"; dataIndex = "address" },
)

private fun isSelect(func: () -> Unit) {
  if (state.selectItem.id == undefined)
    ModalComponent.info(jsObject {
      title = "提示"
      content = "请选择一个用户"
    })
  else func()
}

private fun submit() = MainScope().launch {
  userAdd().onEach {
    message.info("正在执行~~~")
    delay(1000)
    setState(state.copy(isShow = false))
  }.onEach { message.success("操作成功")
  }.onEach { getUserList() }.collect()
}

private fun editUser() = isSelect {
  setState(state.copy(
    title = "编辑员工",
    isShow = true,
    formData = jsObject {
      username = state.selectItem.username
      gender = state.selectItem.gender
      status = state.selectItem.status
      address = state.selectItem.address
    }
  ))
}
private fun showUser() = isSelect {
  message.info("不好意思, 目前该功能正在实现中~~~")
}
private fun deleteUser() = isSelect {
  ModalComponent.confirm(jsObject {
    content = "确定删除员工{ ${state.selectItem.username} }吗?"
    onOk = {
      MainScope().launch {
        userDelete().onEach {
          message.info("正在删除~")
          delay(1000)
          setState(state.copy(isShow = false))
          ModalComponent.destroyAll()
        }.onEach { message.success("删除成功")
        }.onEach { getUserList() }.collect()
      }
      Promise<dynamic> { _, _ -> }
    }
  })

}

private fun getUserList(): Job = MainScope().launch {
  state.current.userList()
    .withKey()
    .onEach { page ->
      page.toPagination {
        state.current = it.toInt()
        getUserList()
      }.let {
        setState(state.copy(
          list = page.list ,
          pagination = it,
          selectRowKey = "0",
          selectItem = page.list[0]
        ))
      }
    }.collect()
}

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

private data class UserState(
  var current: Int = 1,
  val list: Array<User> = emptyArray(),
  val pagination: PaginationConfig = jsObject {  },
  val title: String = "",
  val isShow: Boolean = false,
  val selectItem: User = jsObject {  },
  val formData: User = jsObject {  },
  val selectRowKey: String = "0",
)

private lateinit var state: UserState
private lateinit var setState: (UserState) -> Unit

private val user = functionalComponent<RProps> {
  useState(UserState()).run { state = first; setState = second }

  useEffect(emptyList()) { getUserList() }

  card {
    filterForm(filterFormList) { }
  }
  card { attrs { style = jsObject { marginTop = 10.px } }
    button {
      attrs {
        type = "primary"
        icon = "plus"
        onClickCapture = {
          setState(state.copy(
            title = "创建员工",
            isShow = true,
            formData = jsObject {  }
          ))
        }
      }; +"创建员工"
    }
    button {
      attrs {
        type = "primary"
        icon = "edit"
        style = jsObject { marginLeft = 20.px }
        onClickCapture = { editUser() }
      }; +"编辑员工"
    }
    button {
      attrs {
        type = "primary"
        icon = "eye"
        style = jsObject { marginLeft = 20.px }
        onClickCapture = { showUser() }
      }; +"员工详情"
    }
    button {
      attrs {
        type = "primary"
        icon = "delete"
        style = jsObject { marginLeft = 20.px }
        onClickCapture = { deleteUser() }
      }; +"删除员工"
    }
  }
  contentWrap {
    table<User, TableComponent<User>> {
      attrs {
        rowSelection = jsObject { type = "radio"; selectedRowKeys = state.selectRowKey }
        bordered = true
        columns = userTableHeader
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
      title = state.title
      visible = state.isShow
      width = 600
      onOk = { submit() }
      onCancel = { setState(state.copy(isShow = false)) }
    }
    form { attrs { layout = "horizontal" }
      formItem({ label = "用户名" }) {
        input {
          attrs {
            placeholder = "请输入用户名";
            value = state.formData.username
            onChangeCapture = {
              setState(state.copy(
                formData = state.formData.apply {
                  username = it.target.unsafeCast<INPUT>().value
                }
              ))
            }
          }
        }
      }
      formItem({ label = "性别" }) {
        radioGroup {
          attrs {
            value = state.formData.gender.let { if (it == undefined) "1" else "$it" }
            onChange = {
              setState(state.copy(
                formData = state.formData.apply {
                  gender = it.target.unsafeCast<INPUT>().value.toInt()
                }
              ))
            }
          }
          radio { attrs { value = "1" }; + "男" }
          radio { attrs { value = "2" }; + "女" }
        }
      }
      formItem({ label = "状态" }) {
        select<String, SelectComponent<String>> {
          attrs {
            placeholder = "请选择状态"
            value = state.formData.status.let { if (it == undefined) "1" else "$it" }
            onChange = { value, _ ->
              setState(state.copy(
                formData = state.formData.apply {
                  status = value.toInt()
                }
              ))
            }
          }
          option { attrs { value = "1" }; +"咸鱼一条" }
          option { attrs { value = "2" }; +"风华浪子" }
          option { attrs { value = "3" }; +"北大才子一枚" }
          option { attrs { value = "4" }; +"百度FE" }
          option { attrs { value = "5" }; +"创业者" }
        }
      }
      formItem({ label = "生日" }) {
        datePicker {  }
      }
      formItem({ label = "联系地址" }) {
        textArea {
          attrs {
            rows = 3
            placeholder = "请输入联系地址"
            value = state.formData.address
            onChangeCapture = {
              setState(state.copy(
                formData = state.formData.apply {
                  address = it.target.unsafeCast<INPUT>().value
                }
              ))
            }
          }
        }
      }
    }
  }
}

fun RBuilder.user() = child(user)