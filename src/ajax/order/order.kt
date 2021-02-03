package ajax.order

import ajax.Page
import ajax.ajax
import ajax.bike.BikeInfo
import ajax.bike.Position
import ajax.bike.Service
import kotlinx.coroutines.flow.Flow

interface OrderDetail {
  var id: Int
  var userId: Int
  var userName: String
  var totalFee: Int
  var userPay: Int
  var status: Int
  var orderSn: dynamic
  var bikeSn: String
  var mode: Int
  var startLocation: String
  var endLocation: String
  var cityId: Int
  var mobile: dynamic
  var username: String
  var distance: Int
  var bikeGps: String
  var startTime: String
  var endTime: String
  var totalTime: Int
  var positionList: Array<Position>
  var area: Array<Service>
  var nplList: Array<Npl>
}

interface Npl {
  var id: Int
  var name: String
  var cityId: Int
  var type: Int
  var status: Int
  var mapPoint: String
  var mapPointArray: Array<String>
  var mapStatus: Int
  var creatorName: String
  var createTime: String
}

suspend fun orderDetail(): Flow<OrderDetail> {
  return ajax("/order/detail")
}

suspend fun Int.orderList(): Flow<Page<OrderDetail>> {
  return ajax("/order/list?page=${this}")
}

suspend fun orderBikeInfo(): Flow<BikeInfo> {
  return ajax("/order/bikeInfo", false)
}

suspend fun orderFinish(): Flow<String> {
  return ajax("/order/finish", false)
}