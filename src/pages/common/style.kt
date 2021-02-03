package pages.common


import style.colorM
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.css.color
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.simplePage(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    child("div") {
      backgroundColor = Color("#1890ff")
      color = colorM
    }
    child("div a") { color = Color.yellow }
  }
  func()
}