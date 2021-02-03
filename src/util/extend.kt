package util

import ajax.Page
import antd.pagination.PaginationConfig
import kotlinext.js.jsObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlin.js.Date

fun Date.formatDate(): String {
  return "${getFullYear()}-" +
          "${(getMonth() + 1).isAdd0()}-" +
          "${getDate().isAdd0()} " +
          "${getHours().isAdd0()}:" +
          "${getMinutes().isAdd0()}:" +
          "${getSeconds().isAdd0()}"
}
private fun Int.isAdd0() = if (this <= 10) "0$this" else this

fun <T> Page<T>.toPagination(callback: (Number) -> Unit): PaginationConfig {
  return jsObject {
    onChange = { cur, _ -> callback(cur) }
    current = page
    pageSize = pageSize
    total = totalCount
    showTotal = { _, _ -> "一共${totalCount}条数据" }
    showQuickJumper = true
  }
}

fun <T> Flow<Page<T>>.withKey() = onEach { page ->
  page.list.withIndex().forEach { it.value.asDynamic().key = it.index }
}

