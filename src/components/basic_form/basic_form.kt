package components.basic_form

import antd.button.button
import antd.checkbox.checkbox
import antd.datepicker.datePicker
import antd.form.form
import antd.form.formItem
import antd.input.input
import antd.select.SelectComponent
import antd.select.option
import antd.select.select
import kotlinext.js.js
import kotlinext.js.jsObject
import react.*

interface FormItemData {
  var label: String
  var defaultValue: String
  var placeholder: String
  var width: Int
  var type: String
  var list: Array<OptionData>
}

interface OptionData {
  var id: String
  var name: String
}

private interface FilterFormProps: RProps {
  var formList: Array<FormItemData>
  var submit: () -> Unit
}

private fun RBuilder.initFormList(formList: Array<FormItemData>) {
  formList.withIndex().forEach { item ->
    val label = item.value.label
    val defaultValue = item.value.defaultValue
    val placeholder = item.value.placeholder
    val w = item.value.width
    when(item.value.type) {
      "INPUT" -> formItem {
        attrs.label = label; attrs.key = "!${item.index}"
        input {
          attrs.type = "text"
          attrs.placeholder = placeholder
        }
      }
      "SELECT" -> formItem {
        attrs.label = label; attrs.key = "$${item.index}"
        select<String, SelectComponent<String>> {
          attrs.defaultValue = defaultValue
          attrs.placeholder = placeholder
          attrs.style = jsObject { width = w }
          item.value.list.forEach {
            option { attrs { value = it.id; key = it.id }; +it.name }
          }
        }
      }
      "CHECKBOX" -> formItem {
        attrs.label = label; attrs.key = item.index.toString()
        checkbox { +label }
      }
      "DATE" -> formItem {
        attrs.label = label; attrs.key = "0"
        datePicker { attrs.placeholder = placeholder }
      }
      "TIME" -> {
        formItem {
          attrs.label = "订单时间"; attrs.key = "0"
          datePicker { attrs.placeholder = "请选择开始时间" }
        }
        formItem {
          attrs.label = "~"; attrs.colon = false; attrs.key = "1"
          datePicker { attrs.placeholder = "请选择结束时间" }
        }
      }
    }
  }
}

private val filterForm = functionalComponent<FilterFormProps> { props ->
  form { attrs { layout = "inline" }
    initFormList(props.formList)
    formItem {
      button {
        attrs {
          type = "primary"
          style = js { margin = "0 20px" }
          onClickCapture = { props.submit }
        }; +"查询"
      }
      button { +"重置" }
    }
  }
}

fun RBuilder.filterForm(formList: Array<FormItemData>, submit: () -> Unit) = child(filterForm) {
  attrs.formList = formList
  attrs.submit = submit
}