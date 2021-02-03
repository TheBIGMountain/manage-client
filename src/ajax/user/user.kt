package ajax.user

import ajax.Page
import ajax.ajax
import kotlinx.coroutines.flow.Flow

interface User {
  var id: Int
  var username: String
  var gender: Int
  var status: Int
  var hobby: Int
  var isMarried: Int
  var birthday: String
  var address: String
  var time: String
}

suspend fun userAdd(): Flow<String> {
  return ajax("/user/add", false)
}

suspend fun userEdit(): Flow<String> {
  return ajax("/user/edit", false)
}

suspend fun userDelete(): Flow<String> {
  return ajax("/user/delete", false)
}

suspend fun Int.userList(): Flow<Page<User>> {
  return ajax("/user/list?page=${this}")
}