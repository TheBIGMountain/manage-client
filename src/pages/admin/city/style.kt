package pages.admin.city

import style.colorM
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv


fun RBuilder.contentWrap(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    backgroundColor = colorM
    border(1.px, BorderStyle.solid, colorM)
    marginTop = (-3).px
    child(".ant-table-wrapper") {
      marginLeft = (-1).px
      marginRight = (-1).px
    }
  }
  func()
}