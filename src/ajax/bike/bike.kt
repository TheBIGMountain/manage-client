package ajax.bike

import ajax.ajax
import kotlinx.coroutines.flow.Flow

interface BikeList {
  var totalCount: Int
  var bikeList: Array<String>
  var routeList: Array<String>
  var serviceList: Array<Service>
}

interface BikeInfo {
  var id: Int
  var bikeSn: String
  var battery: Int
  var startTime: String
  var location: String
}

interface Service {
  var lon: String
  var lat: String
}

interface Position {
  var lon: String
  var lat: String
}

suspend fun bikeList(): Flow<BikeList> {
  return ajax("/map/bikeList")
}