package pages.admin.ui.carousel

import style.colorM
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.carouselWrapper(func: StyledDOMBuilder<DIV>.() -> Unit) = styledDiv {
  css {
    child(".ant-card .ant-card-body .ant-carousel .slick-slider.imgSize") {
      height = 400.px
    }
    child(".ant-card .ant-card-body .ant-carousel .slick-slider") {
      textAlign = TextAlign.center
      height = 140.px
      lineHeight = LineHeight(160.px.value)
      backgroundColor = Color("#364d79")
      overflow = Overflow.hidden
    }
    child(".ant-card .ant-card-body .ant-carousel .slick-slider h3") {
      color = colorM
    }
    child(".ant-card .ant-card-body .ant-carousel .slick-slider img") {
      width = 100.pct
      height = 50.pct
      backgroundSize = "contain"
    }
  }
  func()
}