package components.footer

import style.colorJ
import kotlinx.css.*
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.footerSty(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    height = 100.px
    padding(40.px, 0.px)
    textAlign = TextAlign.center
    color = colorJ
  }
  func()
}