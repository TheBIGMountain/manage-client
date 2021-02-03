package components.nav_left

import style.colorM
import style.colorS
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.DIV
import kotlinx.html.H1
import react.RBuilder
import styled.*

fun RBuilder.logo(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    lineHeight = LineHeight(90.px.value)
    backgroundColor = colorS
    paddingLeft = 5.px
  }
  func()
}

fun RBuilder.imgSty() = styledImg {
  css {
    paddingLeft = 20.px
    height = 35.px
    verticalAlign = VerticalAlign.middle
  }
  attrs { src = "/assets/logo-ant.svg" }
}

fun RBuilder.h1Sty(func: StyledDOMBuilder<H1>.() -> Unit) = styledH1 {
  css {
    color = colorM
    fontSize = 20.px
    display = Display.inlineBlock
    margin(0.px, 0.px, 0.px, 10.px)
    verticalAlign = VerticalAlign.middle
  }
  func()
}