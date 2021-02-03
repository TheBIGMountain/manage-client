package pages.admin.no_match

import kotlinx.css.TextAlign
import kotlinx.css.fontSize
import kotlinx.css.px
import kotlinx.css.textAlign
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import styled.css
import styled.styledDiv


private val noMatch = functionalComponent<RProps> {
  styledDiv {
    css {
      textAlign = TextAlign.center
      fontSize = 24.px
    }
    +"404 Not Found!!!"
  }
}

fun RBuilder.noMatch() = child(noMatch)


