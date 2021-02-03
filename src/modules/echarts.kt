@file:JsModule("echarts-for-react")
package modules

import react.RClass
import react.RProps

@JsName("default")
external val reactEcharts: RClass<EchartsProps>

external interface EchartsProps: RProps {
  var option: EchartsOption
  var style: dynamic
}

external interface EchartsOption {
  var title: TitleOption
  var legend: LegendOption
  var tooltip: TooltipOption
  var xAxis: XAxisOption
  var yAxis: YAxisOption
  var series: Array<SeriesOption>
}

external interface TitleOption {
  var text: String
  var x: String
}

external interface LegendOption {
  var orient: String
  var right: Int
  var top: Int
  var bottom: Int
  var data: Array<String>
}

external interface TooltipOption {
  var trigger: String
  var formatter: String
}

external interface XAxisOption {
  var type: String
  var boundaryGap: Boolean
  var data: Array<String>
}

external interface YAxisOption {
  var type: String
}

external interface SeriesOption {
  var name: String
  var type: String
  var radius: Array<String>
  var roseType: String
  var data: Array<SeriesData>
  var areaStyle: dynamic
}

external interface SeriesData {
  var name: String
  var value: Int
}


