package router

import pages.admin.admin
import pages.admin.city.city
import pages.admin.echarts.bar
import pages.admin.echarts.line
import pages.admin.echarts.pie
import pages.admin.form.loginForm
import pages.admin.form.register
import pages.admin.home.home
import pages.admin.map.bikeMap
import pages.admin.no_match.noMatch
import pages.admin.order.order
import pages.admin.permission.permission
import pages.admin.rich.rich
import pages.admin.table.basicTable
import pages.admin.table.highTable
import pages.admin.ui.buttons.buttons
import pages.admin.ui.carousel.carousel
import pages.admin.ui.gallery.gallery
import pages.admin.ui.loading.loading
import pages.admin.ui.messages.messages
import pages.admin.ui.modals.modals
import pages.admin.ui.notification.notification
import pages.admin.ui.tabs.tabs
import pages.admin.user.user
import pages.common.common
import pages.common.order_detail.orderDetail
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import react.router.dom.hashRouter
import react.router.dom.redirect
import react.router.dom.route
import react.router.dom.switch

private val router = functionalComponent<RProps> {
  hashRouter {
    switch {
      route("/common", render = {
        common {
          route("/common/order/detail/:orderId", render = { orderDetail() })
        }
      })
      route("/", render = {
        admin {
          switch {
            route("/home", render = { home() })
            route("/ui/buttons", render = { buttons() })
            route("/ui/modals", render = { modals() })
            route("/ui/loadings", render = { loading() })
            route("/ui/notification", render = { notification() })
            route("/ui/messages", render = { messages() })
            route("/ui/tabs", render = { tabs() })
            route("/ui/gallery", render = { gallery() })
            route("/ui/carousel", render = { carousel() })
            route("/form/login", render = { loginForm() })
            route("/form/reg", render = { register() })
            route("/table/basic", render = { basicTable() })
            route("/table/high", render = { highTable() })
            route("/city", render = { city() })
            route("/order", render = { order() })
            route("/user", render = { user() })
            route("/bikeMap", render = { bikeMap() })
            route("/charts/bar", render = { bar() })
            route("/charts/pie", render = { pie() })
            route("/charts/line", render = { line() })
            route("/rich", render = { rich() })
            route("/permission", render = { permission() })
            redirect("/", "/home", exact = true)
            route("", render = { noMatch() })
          }
        }
      })
    }
  }
}

fun RBuilder.router() = child(router)