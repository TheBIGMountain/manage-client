package components.nav_left

import antd.menu.menu
import antd.menu.menuItem
import antd.menu.subMenu
import react.*
import react.dom.div
import react.router.dom.navLink
import router.Menu
import router.menuList


private fun RBuilder.renderMenu(list: List<Menu>): List<ReactElement> {
  return list.map {
    if (it.children != null)
      return@map subMenu {
        attrs {
          title = it.title
          key = it.key
        }
        renderMenu(it.children)
      }
    return@map menuItem {
      attrs {
        title = it.title
        key = it.key
      }
      navLink(it.key) { +it.title }
    }
  }
}

private val navLeft = functionalComponent<RProps> {
  div {
    logo {
      imgSty()
      h1Sty { +"后台管理" }
    }
    menu { attrs { theme = "dark" }
      renderMenu(menuList)
    }
  }
}

fun RBuilder.navLeft() = child(navLeft)

