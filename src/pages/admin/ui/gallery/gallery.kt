package pages.admin.ui.gallery

import antd.card.card
import antd.card.cardMeta
import antd.grid.col
import antd.grid.row
import kotlinext.js.js
import react.*
import react.dom.img
import kotlin.random.Random

private fun randomImg() = "${Random.nextInt(1, 26)}.png"

private val imgs = arrayOf(
  arrayOf(randomImg(), randomImg(), randomImg(), randomImg(), randomImg()),
  arrayOf(randomImg(), randomImg(), randomImg(), randomImg(), randomImg()),
  arrayOf(randomImg(), randomImg(), randomImg(), randomImg(), randomImg()),
  arrayOf(randomImg(), randomImg(), randomImg(), randomImg(), randomImg()),
  arrayOf(randomImg(), randomImg(), randomImg(), randomImg(), randomImg()),
  arrayOf(randomImg(), randomImg(), randomImg(), randomImg(), randomImg()),
)

private val gallery = functionalComponent<RProps> {
  row { attrs { gutter = 10 }
    imgs.forEach { arr ->
      col { attrs.md = 4
        arr.forEach {
          card {
            attrs {
              style = js { marginBottom = 10 }
              cover = buildElement { img { attrs { alt = "example"; src = "/gallery/$it" } } }
            }
            cardMeta { attrs { title = "Card title"; description = "This is the description" } }
          }
        }
      }
    }
  }
}

fun RBuilder.gallery() = child(gallery)

