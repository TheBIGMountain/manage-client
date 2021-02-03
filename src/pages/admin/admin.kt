package pages.admin

import components.footer.footer
import components.header.header
import components.nav_left.navLeft
import kotlinx.html.DIV
import react.*
import styled.StyledDOMBuilder

private interface AdminProps: RProps {
  var content: RBuilder.() -> Unit
}

private val admin = functionalComponent<AdminProps> { props ->
  navLeftSty { navLeft() }
  main {
    header()
    contentSty { props.content(this) }
    footer()
  }
}

fun RBuilder.admin(content: RBuilder.() -> Unit) = child(admin) { attrs.content = content }

