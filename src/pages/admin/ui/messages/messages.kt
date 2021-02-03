package pages.admin.ui.messages

import antd.button.button
import antd.card.card
import antd.message.message
import pages.admin.ui.modals.cardWrapper
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent


private val messages = functionalComponent<RProps> {
  cardWrapper {
    card { attrs { title = "全局提示框"; className = "card-wrapper" }
      button { attrs { type = "primary"; onClick = { message.success("成功~~~") } }; +"success" }
      button { attrs { type = "primary"; onClick = { message.info("信息~~~") } }; +"info" }
      button { attrs { type = "primary"; onClick = { message.warning("警告~~~") } }; +"warning" }
      button { attrs { type = "primary"; onClick = { message.error("错误~~~") } }; +"error" }
      button { attrs { type = "primary"; onClick = { message.loading("加载~~~") } }; +"loading" }
    }
  }
}

fun RBuilder.messages() = child(messages)