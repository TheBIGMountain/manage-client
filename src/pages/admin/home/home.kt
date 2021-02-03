package pages.admin.home

import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent

private val home = functionalComponent<RProps> {
  homeWrapper { +"欢迎来到后台管理系统" }
}

fun RBuilder.home() = child(home)




