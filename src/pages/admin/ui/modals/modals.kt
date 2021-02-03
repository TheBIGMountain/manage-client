package pages.admin.ui.modals

import antd.button.button
import antd.card.card
import antd.modal.ModalComponent
import antd.modal.modal
import kotlinext.js.jsObject
import react.*
import react.dom.p

private val modals = functionalComponent<RProps> {
  val (showModal1, isShowModal1) = useState(false)
  val (showModal2, isShowModal2) = useState(false)

  cardWrapper {
    card { attrs { title = "基础模态框"; className = "card-wrapper" }
      button { attrs { type = "primary"; onClick = { isShowModal1(true) } }; +"弹框" }
      button { attrs { type = "primary"; onClick = { isShowModal2(true) } }; +"页脚" }
    }
    card { attrs { title = "信息确认框";className = "card-wrapper" }
      button {
        attrs {
          type = "primary"
          onClick = {
            ModalComponent.confirm(jsObject {
              title = "是否确认"
              content = "我是一个确认框"
            })
          }
        }; +"Confirm"
      }
      button {
        attrs {
          type = "primary"
          onClick = {
            ModalComponent.info(jsObject {
              title = "消息"
              content = "我是一个消息框"
            })
          }
        }; +"Info"
      }
      button {
        attrs {
          type = "primary"
          onClick = {
            ModalComponent.success(jsObject {
              title = "成功"
              content = "我是一个成功框"
            })
          }
        }; +"success"
      }
      button {
        attrs {
          type = "primary"
          onClick = {
            ModalComponent.error(jsObject {
              title = "错误"
              content = "我是一个错误框"
            })
          }
        }; +"Error"
      }
      button {
        attrs {
          type = "primary"
          onClick = {
            ModalComponent.warning(jsObject {
              title = "警告"
              content = "我是一个警告框"
            })
          }
        }; +"Warning"
      }
    }
    modal { attrs { title = "弹框"; visible = showModal1; }
      attrs { onCancel = { isShowModal1(false) }; onOk = { isShowModal1(false) } }
      p { +"我是一个弹框" }
    }
    modal { attrs { title = "页脚"; okText = "好的"; cancelText = "算了"; visible = showModal2 }
      attrs { onCancel = { isShowModal2(false) }; onOk = { isShowModal2(false) } }
      p { +"我是一个页脚" }
    }
  }
}

fun RBuilder.modals() = child(modals)