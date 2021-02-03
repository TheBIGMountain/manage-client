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
  title = jsObject { text = "用户骑行订单"; x = "center" }
  legend = jsObject { orient = "vertical"; right = 10; top = 20; bottom = 20
    data = arrayOf("周一", "周二", "周三","周四","周五","周六", "周天")
  }
  tooltip = jsObject { trigger = "item"; formatter = "{a}<br/>{b}: {c}({d}%)" }
  series = arrayOf(
    jsObject { name = "订单量"; type = "pie"; data = arrayOf(
      jsObject { name = "周一"; value = 1000 },
      jsObject { name = "周二"; value = 1000 },
      jsObject { name = "周三"; value = 2000 },
      jsObject { name = "周四"; value = 1500 },
      jsObject { name = "周五"; value = 3000 },
      jsObject { name = "周六"; value = 2000 },
      jsObject { name = "周日"; value = 1200 },
    ) }
  )
}

private val pie = functionalComponent<RProps> {
  card { attrs { title = "饼形图表之一" }
    reactEcharts { attrs.style = jsObject { height = 500.px }
      attrs.option = getOption()
    }
  }
  card { attrs { title = "饼形图表之二"; style = jsObject { marginTop = 10.px } }
    reactEcharts { attrs.style = jsObject { height = 500.px }
      attrs.option = getOption().apply {
        series[0].radius = arrayOf("50%", "80%")
      }
    }
  }
  card { attrs { title = "饼形图表之三"; style = jsObject { marginTop = 10.px } }
    reactEcharts { attrs.style = jsObject { height = 500.px }
      attrs.option = getOption().apply {
        series[0].data.sortBy { it.value }
        series[0].roseType = "radius"
      }
    }
  }
}

fun RBuilder.pie() = child(pie)