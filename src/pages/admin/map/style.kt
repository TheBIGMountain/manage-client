package pages.admin.map

import kotlinx.css.height
import kotlinx.css.px
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.mapContainer(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    height = 500.px
  }
  func()
}