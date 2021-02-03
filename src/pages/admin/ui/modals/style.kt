package pages.admin.ui.modals

import kotlinx.css.marginBottom
import kotlinx.css.marginRight
import kotlinx.css.px
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv


fun RBuilder.cardWrapper(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    child("div.card-wrapper div button") {
      marginRight = 20.px
    }
    child("div") { marginBottom = 20.px }
  }
  func()
}

