
import kotlinext.js.require
import kotlinx.browser.document
import react.dom.render
import style.GlobalStyle
import styled.StyledComponents
import styled.injectGlobal

fun main() {
  require("antd/dist/antd.css")
  require("react-draft-wysiwyg/dist/react-draft-wysiwyg.css")
  StyledComponents.injectGlobal { +GlobalStyle.loading }
  render(document.getElementById("root")) {
    app()
  }
}
