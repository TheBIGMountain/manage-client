package pages.common


import pages.admin.contentSty
import components.header.header
import kotlinx.html.DIV
import react.*
import styled.StyledDOMBuilder

private interface CommonProps: RProps {
  var content: StyledDOMBuilder<DIV>.() -> Unit
}

private val common = functionalComponent<CommonProps> { props ->
  simplePage {
    header("second")
  }
  contentSty {
    props.content(this)
  }
}

fun RBuilder.common(content: RBuilder.() -> Unit) = child(common) { attrs.content = content }

