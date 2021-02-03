package ajax.role

import ajax.Page
import ajax.ajax
import ajax.city.UserInfo
import kotlinx.coroutines.flow.Flow

interface Role {
  var id: Int
  var roleName: String
  var status: Int
  var authorizeUsername: String
  var authorizeTime: String
  var createTime: String
  var menus: Array<String>
}

suspend fun Int.roleList(): Flow<Page<Role>> {
  return ajax("/role/list?page=${this}")
}

suspend fun roleCreate(): Flow<String> {
  return ajax("/role/create", false)
}

suspend fun roleUserList(): Flow<Array<UserInfo>> {
  return ajax("/role/userList")
}

suspend fun userRoleEdit(): Flow<String> {
  return ajax("/role/userRoleEdit", false)
}