package ajax

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ResultVo<T> {
  val code: String
  val result: T
}

interface Page<T> {
  var page: Int
  var pageSize: Int
  var totalCount: Int
  var pageCount: Int
  var list: Array<T>
}

suspend fun <T> ajax(url: String, showLoading: Boolean = true): Flow<T> {
  val e = document.getElementById("ajaxLoading")!!.asDynamic()
  if (showLoading) e.style.display = "block"
  return window.fetch("http://localhost:80/api$url").await()
    .json().await().unsafeCast<ResultVo<T>>()
    .let { e.style.display = "none"; flowOf(it.result) }
}

