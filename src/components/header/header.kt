package components.header


import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.css.marginLeft
import kotlinx.css.px
import react.*
import react.dom.img
import react.dom.span
import styled.css
import styled.styledA
import styled.styledSpan
import util.formatDate
import kotlin.js.Date


private const val username = "TheBIGMountain"

private interface HeaderProps: RProps {
  var menuType: String
}

private fun init(setSysTime: (String) -> Unit) = MainScope().launch {
  while (true) {
    delay(1000);
    setSysTime(Date().formatDate())
  }
}

private val header = functionalComponent<HeaderProps> { props ->
  val (sysTime, setSysTime) = useState("")

  useEffect(emptyList()) { init(setSysTime) }

  headerSty {
    loginInfo {
      if (props.menuType == "second") {
        logo {
          img { attrs.src = "/assets/logo-ant.svg" }
          span { +"东油通用管理系统" }
        }
      }
      span { +"欢迎, ${username}" }
      styledA { css { marginLeft = 40.px }; attrs { href = "#" }; +"退出" }
    }
    if (props.menuType == "")
      breadcrumb {
        breadcrumbTitle { +"首页" }
        weather {
          span { +sysTime }
          styledSpan { css { marginLeft = 10.px }; +"🌥" }
        }
      }
  }
}

fun RBuilder.header(menuType: String = "") = child(header) { attrs.menuType = menuType }