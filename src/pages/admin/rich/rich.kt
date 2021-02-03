package pages.admin.rich

import antd.button.button
import antd.card.card
import antd.modal.modal
import kotlinext.js.jsObject
import kotlinx.css.px
import modules.editor
import react.*

@JsModule("draftjs-to-html")
@JsNonModule
external val draftJs: (String) -> String

private val rich = functionalComponent<RProps> {
  val (editState, setEditState) = useState("")
  val (content, setContent) = useState("")
  val (richText, setRichText) = useState(false)

  card {
    button {
      attrs {
        type = "primary"
        onClickCapture = { setEditState("") }
      }; +"清空内容"
    }
    button {
      attrs {
        type = "primary"
        onClickCapture = { setRichText(true) }
        style = jsObject { marginLeft = 10.px }
      }; +"获取HTML文本"
    }
  }
  card { attrs { title = "副文本编辑器" }
    editor {
      attrs {
        editorState = editState
        onContentStateChange = { setContent(it) }
        onEditorStateChange = { setEditState(it) }
      }
    }
  }
  modal {
    attrs {
      title = "富文本"
      visible = richText
      onCancel = { setRichText(false) }
      footer = null
    }; +draftJs(content)
  }
}

fun RBuilder.rich() = child(rich)