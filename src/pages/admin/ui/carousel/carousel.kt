package pages.admin.ui.carousel

import antd.card.card
import antd.carousel.carousel
import kotlinext.js.js
import react.RBuilder
import react.RProps
import react.child
import react.dom.div
import react.dom.h3
import react.dom.img
import react.functionalComponent


private val carousel = functionalComponent<RProps> {
  carouselWrapper {
    card { attrs { title = "文字背景轮播" }
      carousel { attrs { autoplay = true }
        div { h3 { +"我是第一张图片" } }
        div { h3 { +"我是第二张图片" } }
        div { h3 { +"我是第三张图片" } }
      }
    }
    card { attrs { title = "图片背景轮播"; style = js { height = 510 } }
      carousel { attrs { autoplay = true; className = "imgSize" }
        div { img { attrs.src = "/carousel-img/carousel-1.jpg" } }
        div { img { attrs.src = "/carousel-img/carousel-2.jpg" } }
        div { img { attrs.src = "/carousel-img/carousel-3.jpg" } }
      }
    }
  }
}

fun RBuilder.carousel() = child(carousel)

