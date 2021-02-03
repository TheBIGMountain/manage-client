package pages.admin.echarts

import antd.card.card
import modules.EchartsOption
import modules.reactEcharts
import kotlinext.js.jsObject
import kotlinx.css.px
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent


private fun getOption() = jsObject<EchartsOption> {
  title = jsObject { text = "用户骑行订单" }
  tooltip = jsObject { trigger = "axis" }
  xAxis = jsObject { data = arrayOf("周一", "周二", "周三","周四","周五","周六", "周天") }
  yAxis = jsObject { type = "value" }
}

private val bar = functionalComponent<RProps> {
  card { attrs { title = "柱形图表之一" }
    reactEcharts { attrs.style = jsObject { height = 500.px }
      attrs.option = getOption().apply {
        series = arrayOf(
          jsObject { name = "订单量"; type = "bar"; data = arrayOf(
            jsObject { value = 1000 }, jsObject { value = 2000 }, jsObject { value = 1500 }, jsObject { value = 3000 },
            jsObject { value = 2000 }, jsObject { value = 1200 }, jsObject { value = 1000 },
          ) }
        )
      }
    }
  }
  card { attrs { title = "柱形图表之二"; style = jsObject { marginTop = 10.px } }
    reactEcharts { attrs.style = jsObject { height = 500.px }
      attrs.option = getOption().apply {
        legend = jsObject { data = arrayOf("OFO", "摩拜", "小蓝") }
        series = arrayOf(
          jsObject { name = "OFO"; type = "bar"; data = arrayOf(
            jsObject { value = 2000 }, jsObject { value = 3000 }, jsObject { value = 5500 }, jsObject { value = 7000 },
            jsObject { value = 8000 }, jsObject { value = 12000 }, jsObject { value = 20000 },
          ) },
          jsObject { name = "摩拜"; type = "bar"; data = arrayOf(
            jsObject { value = 1500 }, jsObject { value = 3000 }, jsObject { value = 4500 }, jsObject { value = 6000 },
            jsObject { value = 8000 }, jsObject { value = 10000 }, jsObject { value = 15000 },
          ) },
          jsObject { name = "小蓝"; type = "bar"; data = arrayOf(
            jsObject { value = 1000 }, jsObject { value = 2000 }, jsObject { value = 2500 }, jsObject { value = 4000 },
            jsObject { value = 6000 }, jsObject { value = 7000 }, jsObject { value = 8000 },
          ) },
        )
      }
    }
  }
}

fun RBuilder.bar() = child(bar)