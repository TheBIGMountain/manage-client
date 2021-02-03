package pages.admin.form

import antd.button.button
import antd.card.card
import antd.checkbox.checkbox
import antd.form.form
import antd.form.formItem
import antd.icon.icon
import antd.input.input
import kotlinext.js.js
import kotlinx.css.Float
import kotlinx.html.INPUT
import kotlinx.html.style
import react.*
import react.dom.a

private val loginForm = functionalComponent<RProps> {
  val (username, setUsername) = useState("")
  val (password, setPassword) = useState("")

  card { attrs { title="登录行内表单" }
    form { attrs { layout = "inline" }
      formItem {
        input { attrs { placeholder = "请输入用户名" } }
      }
      formItem {
        input { attrs { placeholder = "请输入密码" } }
      }
      formItem {
        button { attrs { type = "primary" }; +"登录" }
      }
    }
  }
  card { attrs { title = "登录水平表单 "; style = js { marginTop = 10 } }
    form { attrs { style = js { width = 300 } }
      formItem {
        input {
          attrs {
            placeholder = "请输入用户名"
            value = username
            prefix = buildElement { icon { attrs { type = "user" } } }
            onChangeCapture = { setUsername(it.target.unsafeCast<INPUT>().value) }
        } }
      }
      formItem {
        input {
          attrs {
            placeholder = "请输入密码"
            value = password
            type = "password"
            prefix = buildElement { icon { attrs { type = "lock" } } }
            onChangeCapture = { setPassword(it.target.unsafeCast<INPUT>().value) }
        } }
      }
      formItem {
        checkbox { attrs { defaultChecked = true }; +"记住密码" }
        a { attrs { href = "#"; style = js { float = Float.right }.unsafeCast<String>() }; +"忘记密码" }
      }
      formItem {
        button { attrs { type = "primary" }; +"登录" }
      }
    }
  }
}

fun RBuilder.loginForm() = child(loginForm)


