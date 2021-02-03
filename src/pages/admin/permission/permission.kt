package pages.admin.permission

import ajax.city.permissionEdit
import ajax.role.*
import antd.button.button
import antd.card.card
import antd.form.FormItemProps
import antd.form.form
import antd.form.formItem
import antd.input.input
import antd.message.message
import antd.modal.modal
import antd.pagination.PaginationConfig
import antd.select.SelectComponent
import antd.select.option
import antd.select.select
import antd.table.ColumnProps
import antd.table.TableComponent
import antd.table.table
import antd.transfer.TransferItem
import antd.transfer.transfer
import antd.tree.tree
import antd.tree.treeNode
import kotlinext.js.jsObject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.css.px
import pages.admin.city.contentWrap
import react.*
import router.Menu
import router.menuList
import util.formatDate
import util.toPagination
import kotlin.js.Date

private val permissionColumns = arrayOf<ColumnProps<dynamic>> (
  jsObject { title = "角色ID"; dataIndex = "id" },
  jsObject { title = "角色名称"; dataIndex = "roleName" },
  jsObject { title = "创建时间"; dataIndex = "createTime"; render = { item, _, _ ->
    Date(item.toString().toLong()).formatDate()
  } },
  jsObject { title = "使用状态"; dataIndex = "status"; render = { item, _, _ ->
    if (item.toString().toInt() == 1) "启用" else "停用"
  } },
  jsObject { title = "授权时间"; dataIndex = "authorizeTime"; render = { item, _, _ ->
    Date(item.toString().toLong()).formatDate()
  } },
  jsObject { title = "授权人"; dataIndex = "authorizeUsername" },
)

private fun getRoleList(): Job = MainScope().launch {
  state.current.roleList()
    .onEach { page ->
      page.toPagination {
        state.current = it.toInt()
        getRoleList()
      }.let { pagination ->
        page.list.withIndex().forEach { it.value.asDynamic().key = it.index }
        setState(state.copy(
          list = page.list,
          pagination = pagination,
          selectRowKey = "0",
          selectItem = page.list[0]
        ))
      }
    }.collect()
}

private fun getRoleUserList() = MainScope().launch {
  roleUserList().onEach { userList ->
    val transferItems = async {
      userList.map {
        jsObject<TransferItem> {
          key = it.userId.toString()
          title = it.userName
        }
      }.toList().toTypedArray()
    }
    val targetKeys = async {
      userList.filter { it.status == 1 }
        .map { it.userId.toString() }
        .toList().toTypedArray()
    }
    setState(state.copy(
      isShowUser = true,
      transferItems = transferItems.await(),
      targetKeys = targetKeys.await()
    ))
  }.collect()
}

private fun handleCreate(): Job = MainScope().launch {
  roleCreate().onEach {
    message.info("正在创建~~~")
    delay(1000)
    setState(state.copy(isShowCreate = false))
  }.onEach { message.success("创建成功")
  }.onEach { getRoleList() }.collect()
}

private fun handlePermission() = MainScope().launch {
  permissionEdit().onEach {
    message.info("正在授权~~~")
    delay(1000)
    setState(state.copy(isShowEdit = false))
  }.onEach { message.success("授权成功")
  }.onEach { getRoleList() }.collect()
}

private fun handleUserAuth() = MainScope().launch {
  userRoleEdit().onEach {
    message.info("正在授权~~~")
    delay(1000)
    setState(state.copy(isShowUser = false))
  }.onEach { message.success("授权成功")
  }.onEach { getRoleList() }.collect()
}

private val formItemLayout = jsObject<FormItemProps> {
  labelCol = jsObject { span = 5 }
  wrapperCol = jsObject { span = 19 }
}

private fun RBuilder.formItem(props: FormItemProps.() -> Unit, content: RBuilder.() -> Unit) {
  formItem {
    attrs {
      labelCol = formItemLayout.labelCol
      wrapperCol = formItemLayout.wrapperCol
      props()
    }
    content()
  }
}

private fun RBuilder.status() {
  formItem({ label = "状态" }) {
    select<String, SelectComponent<String>> {
      attrs { defaultValue = "1" }
      option { attrs { value = "1" }; +"开启" }
      option { attrs { value = "2" }; +"关闭" }
    }
  }
}

private fun RBuilder.createRoleModal() {
  modal {
    attrs {
      title = "创建角色"
      visible = state.isShowCreate
      width = 600
      onOk = { handleCreate() }
      onCancel = { setState(state.copy(isShowCreate = false)) }
    }
    form { attrs { layout = "horizontal" }
      formItem({ label = "角色名称" }) {
        input { attrs { placeholder = "请输入角色名称" } }
      }
      status()
    }
  }
}

private fun RBuilder.handlePermissionModal() {
  modal {
    attrs {
      title = "设置权限"
      visible = state.isShowEdit
      width = 600
      onOk = { handlePermission() }
      onCancel = { setState(state.copy(isShowEdit = false)) }
    }
    form { attrs { layout = "horizontal" }
      formItem({ label = "角色名称" }) {
        input { attrs { disabled = true; placeholder = state.selectItem.roleName } }
      }
      status()
      tree {
        attrs {
          checkable = true
          checkedKeys = state.selectItem.menus
          onCheck = { keys, _ ->
            setState(state.copy(
              selectItem = state.selectItem.apply {
                menus = keys.unsafeCast<Array<String>>()
              }
            ))
          }
        }
        treeNode {
          attrs {
            title = "平台权限"
            key = "platform_all"
          }
          renderTree(menuList.toTypedArray())
        }
      }
    }
  }
}

private fun RBuilder.renderTree(menuList: Array<Menu>) {
  menuList.forEach {
    if (it.children != null)
      treeNode { attrs { title = it.title; key = it.key }
        renderTree(it.children.toTypedArray())
      }
    else treeNode { attrs { title = it.title; key = it.key } }
  }
}

private fun RBuilder.handleUserAuthModal() {
  modal {
    attrs {
      title = "用户授权"
      visible = state.isShowUser
      width = 800
      onOk = { handleUserAuth() }
      onCancel = { setState(state.copy(isShowUser = false)) }
    }
    form { attrs { layout = "horizontal" }
      formItem({ label = "角色名称" }) {
        input { attrs { disabled = true; placeholder = state.selectItem.roleName } }
      }
      formItem({ label = "用户选择" }) {
        transfer {
          attrs {
            dataSource = state.transferItems
            targetKeys = state.targetKeys
            titles = arrayOf("待选用户", "已选用户")
            render = { it.title }
            showSearch = true
            listStyle = jsObject { width = 190; height = 300 }
            filterOption = { inputValue, item -> item.title.indexOf(inputValue) > -1 }
            onChange = { keys, _, _ -> setState(state.copy(targetKeys = keys)) }
            attrs.locale = jsObject {
              asDynamic().searchPlaceholder = "请输入用户名"
              asDynamic().itemUnit = "项"
              asDynamic().itemsUnit = "项"
            }
          }
        }
      }
    }
  }
}

private fun RBuilder.buttonGroup() {
  card {
    button {
      attrs {
        type = "primary"
        onClickCapture = { setState(state.copy(isShowCreate = true)) }
      }; +"创建角色"
    }
    button {
      attrs {
        type = "primary"
        style = jsObject { marginLeft = 10.px }
        onClickCapture = { setState(state.copy(isShowEdit = true)) }
      }; +"设置权限"
    }
    button {
      attrs {
        type = "primary"
        style = jsObject { marginLeft = 10.px }
        onClickCapture = { getRoleUserList() }
      }; +"用户授权"
    }
  }
}

private fun RBuilder.table() {
  contentWrap {
    table<Role, TableComponent<Role>> {
      attrs {
        rowSelection = jsObject { type = "radio"; selectedRowKeys = state.selectRowKey }
        columns = permissionColumns
        dataSource = state.list
        bordered = true
        pagination = state.pagination
        onRow = { item, i -> jsObject {
          onClick = { setState(state.copy(selectRowKey = "${i.toInt()}", selectItem = item)) }
        } }
      }
    }
  }
}

private data class RoleState(
  var current: Int = 1,
  val list: Array<Role> = emptyArray(),
  val pagination: PaginationConfig = jsObject {  },
  val selectRowKey: String = "0",
  val isShowCreate: Boolean = false,
  val isShowEdit: Boolean = false,
  val isShowUser: Boolean = false,
  val selectItem: Role = jsObject {  },
  val transferItems: Array<TransferItem> = emptyArray(),
  val targetKeys: Array<String> = emptyArray()
)

private lateinit var state: RoleState
private lateinit var setState: (RoleState) -> Unit

private val permission = functionalComponent<RProps> {
  useState(RoleState()).run { state = first; setState = second }

  useEffect(emptyList()) { getRoleList() }

  buttonGroup()
  table()
  createRoleModal()
  handlePermissionModal()
  handleUserAuthModal()
}

fun RBuilder.permission() = child(permission)