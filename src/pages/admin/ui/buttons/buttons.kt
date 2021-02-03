package pages.admin.ui.buttons

import antd.button.button
import antd.button.buttonGroup
import antd.card.card
import antd.radio.radio
import antd.radio.radioGroup
import react.*

private val buttons = functionalComponent<RProps> {
  val (load, isLoad) = useState(true)
  val (btnSize, setBtnSize) = useState("default")

  buttonsWrapper {
    card { attrs { title = "基础按钮"; className = "card-wrapper" }
      button { attrs { type = "primary" }; +"按钮" }
      button { +"按钮" }
      button { attrs { type = "dashed" }; +"按钮" }
      button { attrs { type = "danger" }; +"按钮" }
      button { attrs { disabled = true }; +"按钮" }
    }
    card { attrs { title = "图形按钮"; className = "card-wrapper" }
      button { attrs { icon = "plus" }; +"创建" }
      button { attrs { icon = "edit" }; +"编辑" }
      button { attrs { icon = "delete" }; +"删除" }
      button { attrs { shape = "circle"; icon = "search" } }
      button { attrs { icon = "search"; type = "primary" }; +"搜索" }
      button { attrs { type = "primary"; icon = "download" }; +"下载" }
    }
    card { attrs { title = "Loading按钮"; className = "card-wrapper" }
      button { attrs { type = "primary"; loading = load }; +"确定" }
      button { attrs { type = "primary"; shape = "circle"; loading = load } }
      button { attrs { loading = load }; +"点击加载" }
      button { attrs { shape = "circle"; loading = load } }
      button { attrs { type = "primary"; onClick = { isLoad(!load) } }; +"关闭" }
    }
    card { attrs { title = "按钮组" }
      buttonGroup {
        button { attrs { icon = "left"; type = "primary" }; +"返回" }
        button { attrs { icon = "right";type = "primary" }; +"前进" }
      }
    }
    card { attrs { title = "按钮尺寸"; className = "card-wrapper" }
      radioGroup { attrs { value = btnSize; onChange = { setBtnSize(it.target.value.unsafeCast<String>()) } }
        radio { attrs { value = "small" }; +"小" }
        radio { attrs { value = "default" }; +"中" }
        radio { attrs { value = "large" }; +"大" }
      }
      button { attrs { type = "primary"; size = btnSize }; +"按钮" }
      button { attrs { size = btnSize }; +"按钮" }
      button { attrs { type = "dashed"; size = btnSize }; +"按钮" }
      button { attrs { type = "danger"; size = btnSize }; +"按钮" }
    }
  }
}

fun RBuilder.buttons() = child(buttons)



