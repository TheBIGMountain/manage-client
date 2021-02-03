package components.footer

import react.*

private val footer = functionalComponent<RProps> {
  footerSty { +"技术支持: 东北石油大学&TheBIGMountain" }
}

fun RBuilder.footer() = child(footer)