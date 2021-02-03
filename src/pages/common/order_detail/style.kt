package pages.common.order_detail

import style.colorC
import style.colorH
import style.colorU
import style.fontG
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.DIV
import kotlinx.html.UL
import kotlinx.html.id
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv
import styled.styledUl


fun RBuilder.orderMap() = styledDiv {
  css {
    height = 450.px
    margin(0.px, (-22).px)
    marginTop = (-21).px
  }
  attrs.id = "orderDetailMap"
}

fun RBuilder.detailItem(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    marginLeft = 90.px
    padding(25.px, 50.px, 50.px, 0.px)
    borderColor = Color.transparent
  }
  func()
}

fun RBuilder.itemTitle(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    margin(20.px, 0.px)
    fontSize = fontG
    color = colorU
  }
  func()
}

fun RBuilder.detailForm(func: StyledDOMBuilder<UL>.() -> Unit) = styledUl {
  css {
    child("li") {
      listStyleType = ListStyleType.none
      margin(20.px, 0.px)
      lineHeight = LineHeight(20.px.value)
      fontSize = 15.px
      color = colorC
      ::after {
        content = QuotedString("")
        clear = Clear.both
        display = Display.block
        visibility = Visibility.hidden
      }
    }
  }
  func()
}

fun RBuilder.detailFormLeft(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    float = Float.left
    width = 164.px
    textAlign = TextAlign.right
    color = colorH
  }
  func()
}

fun RBuilder.detailFormContent(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css { paddingLeft = 194.px }
  func()
}