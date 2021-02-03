package pages.admin.ui.loading

import antd.alert.alert
import antd.card.card
import antd.spin.spin
import kotlinext.js.jsObject
import kotlinx.css.px
import pages.admin.ui.modals.cardWrapper
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent

private val loading = functionalComponent<RProps> {
  cardWrapper {
    card { attrs { title = "loading图案" }
      spin { attrs { size = "small" } }
      spin { attrs.style = jsObject { marginLeft = 20.px; marginRight = 20.px } }
      spin { attrs { size = "large" } }
    }
    card { attrs { title = "内容遮罩" }
      alert { attrs { message = "遮罩"; description = "我是一个遮罩"; type = "info" } }
      spin {
        alert { attrs { message = "遮罩"; description = "我是一个遮罩"; type = "info" } }
      }
      spin { attrs { tip = "加载中..." }
        alert { attrs { message = "遮罩"; description = "我是一个遮罩"; type = "info" } }
      }
    }
  }
}

fun RBuilder.loading() = child(loading)