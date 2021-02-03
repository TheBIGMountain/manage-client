package ajax.table

import ajax.Page
import ajax.ajax
import kotlinx.coroutines.flow.Flow

interface BasicTable {
  var id: Int
  var username: String
  var age: Int
  var gender: Int
  var status: Int
  var hobby: Int
  var isMarried: Int
  var birthday: String
  var address: String
  var time: String
}

interface HighTable: BasicTable {
  var isMarried1: Int
  var isMarried2: Int
  var isMarried3: Int
  var isMarried4: Int
  var isMarried5: Int
  var isMarried6: Int
  var isMarried7: Int
  var isMarried8: Int
}

suspend fun Int.tableList(): Flow<Page<BasicTable>> {
  return ajax("/table/list?page=${this}")
}

suspend fun Int.tableList1(): Flow<Page<BasicTable>> {
  return ajax("/table/list1?page=${this}")
}

suspend fun Int.tableHighList(): Flow<Page<HighTable>> {
  return ajax("/table/highList?page=${this}")
}