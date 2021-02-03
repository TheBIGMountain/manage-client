package pages.admin.ui.buttons

import kotlinx.css.*
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.buttonsWrapper(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    display = Display.inlineBlock
    width = 100.pct
    child("div.card-wrapper div button") {
      marginRight = 20.px
    }
    child("div") { marginBottom = 20.px }
  }
  func()
}




