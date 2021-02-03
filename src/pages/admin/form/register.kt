package pages.admin.form

import antd.button.button
import antd.card.card
import antd.checkbox.checkbox
import antd.datepicker.datePicker
import antd.form.FormItemProps
import antd.form.form
import antd.form.formItem
import antd.icon.icon
import antd.input.input
import antd.input.textArea
import antd.inputnumber.inputNumber
import antd.message.message
import antd.radio.radio
import antd.radio.radioGroup
import antd.select.SelectComponent
import antd.select.option
import antd.select.select
import antd.switch.switch
import antd.timepicker.timePicker
import antd.upload.HttpRequestHeader
import antd.upload.UploadChangeParam
import antd.upload.UploadFile
import antd.upload.upload
import kotlinext.js.js
import kotlinext.js.jsObject
import react.*
import react.dom.a

private val formItemLayout = jsObject<FormItemProps> {
  labelCol = jsObject { xs = 24; sm = 4 }
  wrapperCol = jsObject { xs = 24; sm = 12 }
}

private val offsetLayout = jsObject<FormItemProps> {
  wrapperCol = jsObject { xs = 24; sm = jsObject { span = 12; offset = 4 } }
}

private fun handleUpload(info: UploadChangeParam<UploadFile>) {
  if (info.file.status == "done") {
    message.success("${info.file.name} file uploaded successfully")
  } else if (info.file.status == "error") {
    message.error("${info.file.name} file upload failed.")
  }
}

private fun RBuilder.formItem(props: FormItemProps.() -> Unit,
                              content: RBuilder.() -> Unit)
= formItem {
  attrs {
    props()
    labelCol = formItemLayout.labelCol
    wrapperCol = formItemLayout.wrapperCol
  }
  content()
}

private val register = functionalComponent<RProps> {
  card { attrs { title = "注册表单" }
    form {
      formItem({ label = "用户名"; required = true }) {
        input {
          attrs {
            placeholder = "请输入用户名"
            prefix = buildElement { icon { attrs { type = "user" } } }
          }
        }
      }
      formItem({ label = "密码" }) {
        input {
          attrs {
            type = "password"
            placeholder = "请输入密码"
            prefix = buildElement { icon { attrs { type = "lock" } } }
          }
        }
      }
      formItem({ label = "性别" }) {
        radioGroup {
          radio { attrs { value = "1" }; +"男" }
          radio { attrs { value = "2" }; +"女" }
        }
      }
      formItem({ label = "年龄" }) {
        inputNumber { attrs { defaultValue = "18" } }
      }
      formItem({ label = "当前状态" }) {
        select<String, SelectComponent<String>> {
          attrs { defaultValue = "1" }
          option { attrs { value = "1" }; +"咸鱼一条" }
          option { attrs { value = "2" }; +"风华浪子" }
          option { attrs { value = "3" }; +"北大才子一枚" }
          option { attrs { value = "4" }; +"百度FE" }
          option { attrs { value = "5" }; +"创业者" }
        }
      }
      formItem({ label = "爱好" }) {
        select<Array<String>, SelectComponent<Array<String>>> {
          attrs { mode = "multiple"; defaultValue = arrayOf("1", "3") }
          option { attrs { value = "1" }; +"游泳" }
          option { attrs { value = "2" }; +"打篮球" }
          option { attrs { value = "3" }; +"踢足球" }
          option { attrs { value = "4" }; +"跑步" }
          option { attrs { value = "5" }; +"爬山" }
          option { attrs { value = "6" }; +"骑行" }
          option { attrs { value = "7" }; +"桌球" }
          option { attrs { value = "8" }; +"麦霸" }
        }
      }
      formItem({ label = "是否已婚" }) {
        switch { attrs { defaultChecked = true } }
      }
      formItem({ label = "生日" }) {
        datePicker { }
      }
      formItem({ label = "联系地址" }) {
        textArea {
          attrs {
            defaultValue = "黑龙江省大庆市东北石油大学"
            autosize = jsObject<dynamic> { minRows = 4; maxRows = 6 }.unsafeCast<Any>()
          }
        }
      }
      formItem({ label = "早起时间" }) {
        timePicker {  }
      }
      formItem({ label = "头像" }) {
        upload {
          attrs {
            name = "file"
            action = "//jsonplaceholder.typicode.com/posts/"
            listType = "picture-card"
            headers = js { authorization = "authorization-text" }.unsafeCast<HttpRequestHeader>()
            onChange = ::handleUpload
          }
          icon { attrs { type = "plus" } }
        }
      }
      formItem { attrs { wrapperCol = offsetLayout.wrapperCol }
        checkbox { +"我已阅读过"; a { attrs.href = "#"; +"东油协议" } }
      }
      formItem { attrs { wrapperCol = offsetLayout.wrapperCol }
        button { attrs.style = js { width = 105 }; +"注册" }
      }
    }
  }
}

fun RBuilder.register() = child(register)

