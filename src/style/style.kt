package style

import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.transform
import kotlinx.css.properties.translate
import styled.StyleSheet

object GlobalStyle: StyleSheet("ajax", isStatic = true) {
  val loading by css {
    display = Display.none
    children(".loading") {
      position = Position.fixed
      top = 50.pct
      left = 50.pct
      transform { translate((-50).pct, (-50).pct) }
      padding(0.px, 40.px)
      height = 80.px
      lineHeight = LineHeight(80.px.value)
      backgroundColor = rgba(0, 0, 0, 0.75)
      borderRadius = 6.px
      textAlign = TextAlign.center
      zIndex = 9999
      fontSize = fontD
      color = colorM
    }
    children(".loading img") {
      width = 32.px
      verticalAlign = VerticalAlign.middle
    }
    children(".loading span") { marginLeft = 12.px }
    children(".overlay") {
      position = Position.fixed
      left = 0.px; right = 0.px; top = 0.px; bottom = 0.px
      zIndex = 9998
      backgroundColor = rgb(255, 255, 255)
      opacity = 0.1
    }
  }
}