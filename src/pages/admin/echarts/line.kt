package pages.admin.echarts

import antd.card.card
import modules.EchartsOption
import modules.SeriesOption
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
  xAxis = jsObject { data = arrayOf("周一", "周二", "周三", "周四", "周五", "周六", "周日") }
  yAxis = jsObject { type = "value" }
  series = arrayOf(
    jsObject { name = "订单量"; type = "line"; data = arrayOf(
      jsObject { value = 1000 },
      jsObject { value = 2000 },
      jsObject { value = 1500 },
      jsObject { value = 3000 },
      jsObject { value = 2000 },
      jsObject { value = 1200 },
      jsObject { value = 800 },
    ) }
  )
}

private val line = functionalComponent<RProps> {
  card { attrs { title = "折线图表之一" }
    reactEcharts { attrs.style = jsObject { height = 500.px }
      attrs.option = getOption()
    }
  }
  card { attrs { title = "折线图表之二"; style = jsObject { marginTop = 10.px } }
    reactEcharts { attrs.style = jsObject { height = 500.px }
      attrs.option = getOption().apply {
        legend = jsObject { data = arrayOf("OFO订单量", "摩拜订单量") }
        series = mutableListOf<SeriesOption>().apply {
          add(jsObject { name = "OFO订单量"; type = "line"; data = arrayOf(
            jsObject { value = 1200 },
            jsObject { value = 3000 },
            jsObject { value = 4500 },
            jsObject { value = 6000 },
            jsObject { value = 8000 },
            jsObject { value = 12000 },
            jsObject { value = 20000 },
          ) })
          add(jsObject { name = "摩拜订单量"; type = "line"; data = arrayOf(
            jsObject { value = 1000 },
            jsObject { value = 2000 },
            jsObject { value = 5500 },
            jsObject { value = 6000 },
            jsObject { value = 8000 },
            jsObject { value = 10000 },
            jsObject { value = 12000 },
          ) })
        }.toTypedArray()
      }
    }
  }
  card { attrs { title = "折线图表之三"; style = jsObject { marginTop = 10.px } }
    reactEcharts { attrs.style = jsObject { height = 500.px }
      attrs.option = getOption().apply {
        xAxis.type = "category"
        xAxis.boundaryGap = false
        series[0].areaStyle = jsObject {  }
      }
    }
  }
}

fun RBuilder.line() = child(line)