package pages.admin

import style.colorL
import style.colorM
import style.colorR
import kotlinx.css.*
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.navLeftSty(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    float = Float.left
    backgroundColor = colorR
    color = colorM
    width = 20.pct
    put("height", "calc(100vh)")
    children("div ul") {
      paddingLeft = 15.px
    }
  }
  func()
}

fun RBuilder.main(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    marginLeft = 20.pct
    backgroundColor = colorL
    width = 80.pct
    overflow = Overflow.auto
    put("height", "calc(100vh)")
  }
  func()
}

fun RBuilder.contentSty(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    position = Position.relative
    padding(20.px)
  }
  func()
}

