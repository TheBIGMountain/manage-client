package ajax.city

import ajax.Page
import ajax.ajax
import kotlinx.coroutines.flow.Flow

interface UserInfo {
  var userName: String
  var userId: Int
  var status: Int
}

interface CityInfo {
  var id: Int
  var name: String
  var mode: Int
  var opMode: Int
  var franchiseeId: Int
  var franchiseeName: String
  var cityAdmins: Array<UserInfo>
  var openTime: String
  var sysUsername: String
  var updateTime: String
}

suspend fun openCity(): Flow<String> {
  return ajax("/city/open", false)
}

suspend fun Int.openCities(): Flow<Page<CityInfo>> {
  return ajax("/openCities?page=${this}")
}

suspend fun permissionEdit(): Flow<String> {
  return ajax("/permission/edit", false)
}


