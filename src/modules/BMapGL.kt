package modules

external object BMapGL {
  interface Control
  class ScaleControl(option: ControlOption = definedExternally): Control
  class NavigationControl(option: ControlOption = definedExternally): Control

  class Map(id: String) {
    fun addControl(control: Control)
    fun centerAndZoom(point: dynamic, i: Int)
    fun addOverlay(overlay: dynamic)
  }
  class Point(x: Double, y: Double)
  class Icon(src: String, size: Size, option: IconOption)
  class Size(x: Double, y: Double)
  class Marker(point: Point, option: MarkerOption)
  class Polyline(trackPoints: Array<Point>, option: PolyOption)
  class Polygon(trackPoints: Array<Point>, option: PolyOption)
}

external interface ControlOption {
  var anchor: dynamic
}

external interface IconOption {
  var imageSize: BMapGL.Size
  var anchor: BMapGL.Size
}

external interface MarkerOption {
  var icon: BMapGL.Icon
}

external interface PolyOption {
  var strokeColor: String
  var strokeWeight: Int
  var strokeOpacity: Double
  var fillColor: String
  var fillOpacity: Double
}


