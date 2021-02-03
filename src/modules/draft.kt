@file:JsModule("react-draft-wysiwyg")
package modules

import react.RClass
import react.RProps

@JsName("Editor")
external val editor: RClass<EditorProps>

external interface EditorProps: RProps {
  var editorState: String
  var onContentStateChange: (String) -> Unit
  var onEditorStateChange: (String) -> Unit
}
