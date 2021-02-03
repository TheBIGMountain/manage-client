package pages.admin.ui.tabs

import antd.card.card
import antd.icon.icon
import antd.message.message
import antd.tabs.TabsEditAction
import antd.tabs.tabPane
import antd.tabs.tabs
import kotlinext.js.jsObject
import pages.admin.ui.modals.cardWrapper
import react.*
import react.dom.span

private var newTabIndex = 0
private fun add() {
  // 添加新页签
  val newKey = "newTab${newTabIndex++}"
  val newPanes = state.panes.toMutableList().apply { add(jsObject {
    title = "New Tab"
    content = "Content of new Tab"
    key = newKey
  })}.toTypedArray()

  // 修改数据
  setState(state.copy(panes = newPanes, activeKey = newKey))
}

private fun remove(targetKey: String) {
  // 获取tab页签最后一个索引
  var lastIndex = 0
  state.panes.forEachIndexed { i, pane -> if (pane.key == targetKey) lastIndex = i + 1 }

  // 过滤待删除的页签
  val tabPanes = state.panes.filter { pane -> pane.key != targetKey }.toTypedArray()

  // 获取当前激活的tab
  var key = state.activeKey
  if (tabPanes.isNotEmpty() && key == targetKey) {
    key = if (lastIndex == 0) tabPanes[lastIndex].key else tabPanes[0].key
  }

  // 修改数据
  setState(state.copy(panes = tabPanes, activeKey = key))
}

private fun handleEdit(targetKey: Any, action: TabsEditAction) = when (action) {
  "add" -> add()
  "remove" -> remove(targetKey.unsafeCast<String>())
  else -> { message.error("不可执行的操作"); Unit }
}

private lateinit var state: TabsState
private lateinit var setState: (TabsState) -> Unit

private interface TabsContent {
  var title: String
  var content: String
  var key: String
}

private data class TabsState(
  val panes: Array<TabsContent> = arrayOf(
    jsObject { title = "Tab 1"; content = "Content of Tab 1"; key = "1" },
    jsObject { title = "Tab 2"; content = "Content of Tab 2"; key = "2" },
    jsObject { title = "Tab 3"; content = "Content of Tab 3"; key = "3" },
  ),
  val activeKey: String = panes[0].key
)

private val tabs = functionalComponent<RProps> {
  useState(TabsState()).run { state = first; setState = second }

  cardWrapper {
    card { attrs { title = "Tab页签"; className = "card-wrapper" }
      tabs { attrs { defaultActiveKey = "1"; onChange = { message.info("你选中了页签: $it") } }
        tabPane { attrs { tab = "Tab1"; key = "1" }; +"页签1~~~" }
        tabPane { attrs { tab = "Tab2"; key = "2" }; +"页签2~~~" }
        tabPane { attrs { tab = "Tab2"; key = "3" }; +"页签3~~~" }
      }
    }
    card { attrs { title = "Tab带图标的页签"; className = "card-wrapper" }
      tabs { attrs { defaultActiveKey = "1"; onChange = { message.info("你选中了页签: $it") } }
        tabPane { attrs { tab = buildElement { span { icon { attrs.type = "plus" }; +"Tab1" } }; key = "1" }; +"页签1~~~" }
        tabPane { attrs { tab = buildElement { span { icon { attrs.type = "edit" }; +"Tab2" } }; key = "2" }; +"页签2~~~" }
        tabPane { attrs { tab = buildElement { span { icon { attrs.type = "delete" }; +"Tab3" } }; key = "3" }; +"页签3~~~" }
      }
    }
    card { attrs { title = "Tab可编辑的页签"; className = "card-wrapper" }
      tabs {
        attrs {
          activeKey = state.activeKey
          type = "editable-card"
          onChange = { setState(state.copy(activeKey = it)) }
          onEdit = ::handleEdit
        }
        state.panes.forEach {
          tabPane { attrs { tab = it.title; key = it.key }; +it.content }
        }
      }
    }
  }
}

fun RBuilder.tabs() = child(tabs)