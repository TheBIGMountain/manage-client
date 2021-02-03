package router

class Menu(
  val title: String,
  val key: String,
  val children: List<Menu>? = null
)

val menuList = listOf(
  Menu("首页", "/home"),
  Menu(
    "UI", "/ui", listOf(
      Menu("按钮", "/ui/buttons"),
      Menu("弹框", "/ui/modals"),
      Menu("加载", "/ui/loadings"),
      Menu("通知提醒", "/ui/notification"),
      Menu("全局Message", "/ui/messages"),
      Menu("Tab页签", "/ui/tabs"),
      Menu("图片画廊", "/ui/gallery"),
      Menu("轮播图", "/ui/carousel")
    )
  ),
  Menu(
    "表单", "/form", listOf(
      Menu("登录", "/form/login"),
      Menu("注册", "/form/reg")
    )
  ),
  Menu(
    "表格", "/table", listOf(
      Menu("基础表格", "/table/basic"),
      Menu("高级表格", "/table/high")
    )
  ),
  Menu("富文本", "/rich"),
  Menu("城市管理", "/city"),
  Menu("订单管理", "/order"),
  Menu("员工管理", "/user"),
  Menu("车辆地图", "/bikeMap"),
  Menu(
    "图表", "/charts", listOf(
      Menu("柱状图", "/charts/bar"),
      Menu("饼图", "/charts/pie"),
      Menu("折线图", "/charts/line")
    )
  ),
  Menu("权限设置", "/permission")
)

