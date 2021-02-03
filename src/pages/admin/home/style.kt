package pages.admin.home

import style.colorM
import kotlinx.css.*
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.homeWrapper(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    backgroundColor = colorM
    put("height", "calc(60vh)")
    display = Display.flex
    alignItems = Align.center
    justifyContent = JustifyContent.center
    fontSize = 20.px
  }
  func()
}