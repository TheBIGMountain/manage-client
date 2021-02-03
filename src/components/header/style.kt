package components.header

import style.colorM
import style.fontC
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.headerSty(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css { backgroundColor = colorM }
  func()
}

fun RBuilder.loginInfo(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    height = 60.px
    lineHeight = LineHeight(60.px.value)
    padding(0.px, 20.px)
    textAlign = TextAlign.right
  }
  func()
}

fun RBuilder.logo(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    display = Display.inlineBlock
    lineHeight = LineHeight(60.px.value)
    float = Float.left
    fontSize = 18.px
    child("span") { marginLeft = 10.px; verticalAlign = VerticalAlign.middle }
    child("img") { height = 40.px; verticalAlign = VerticalAlign.middle  }
  }
  func()
}

fun RBuilder.breadcrumb(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    height = 40.px
    lineHeight = LineHeight(40.px.value)
    padding(0.px, 20.px)
    borderTop = "1px solid #0090ff"
  }
  func()
}

fun RBuilder.breadcrumbTitle(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    textAlign = TextAlign.center
    fontSize = fontC
    float = Float.left
    width = 20.pct
    position = Position.relative
    ::after {
      position = Position.absolute
      content = QuotedString("")
      left = 118.px
      top = 39.px
      borderTop = "9px solid ${colorM.value}"
      borderLeft = "12px solid transparent"
      borderRight = "12px solid transparent"
    }
  }
  func()
}

fun RBuilder.weather(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    marginLeft = 20.pct
    textAlign = TextAlign.right
  }
  func()
}
