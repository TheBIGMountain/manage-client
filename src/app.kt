import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import router.router

private val app = functionalComponent<RProps> {
  router()
}

fun RBuilder.app() = child(app)