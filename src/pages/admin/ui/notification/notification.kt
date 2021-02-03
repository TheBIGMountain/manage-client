package pages.admin.ui.notification

import antd.button.button
import antd.card.card
import antd.notification.notification
import kotlinext.js.jsObject
import pages.admin.ui.modals.cardWrapper
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent

private fun showSuccess(direction: String = "topRight") = notification.success(jsObject {
  message = "成功提示框"
  description = "我是一个成功提示框"
  placement = direction
})

private fun showInfo(direction: String = "topRight") = notification.info(jsObject {
  message = "信息提示框"
  description = "我是一个信息提示框"
  placement = direction
})

private fun showWarning(direction: String = "topRight") = notification.warning(jsObject {
  message = "警告提示框"
  description = "我是一个警告提示框"
  placement = direction
})

private fun showError(direction: String = "topRight") = notification.error(jsObject {
  message = "错误提示框"
  description = "我是一个错误提示框"
  placement = direction
})

private val notifications = functionalComponent<RProps> {
  cardWrapper {
    card { attrs { title = "通知提醒"; className = "card-wrapper" }
      button { attrs { type = "primary"; onClick = { showSuccess() } }; +"success" }
      button { attrs { type = "primary"; onClick = { showInfo() } }; +"info" }
      button { attrs { type = "primary"; onClick = { showWarning() } }; +"warning" }
      button { attrs { type = "primary"; onClick = { showError() } }; +"error" }
    }
    card { attrs { title = "弹出方向"; className = "card-wrapper" }
      button { attrs { type = "primary"; onClick = { showSuccess("topLeft") } }; +"左上角弹出" }
      button { attrs { type = "primary"; onClick = { showInfo("topRight") } }; +"右上角弹出" }
      button { attrs { type = "primary"; onClick = { showWarning("bottomLeft") } }; +"左下角弹出" }
      button { attrs { type = "primary"; onClick = { showError("bottomRight") } }; +"右下角弹出" }
    }
  }
}

fun RBuilder.notification() = child(notifications)