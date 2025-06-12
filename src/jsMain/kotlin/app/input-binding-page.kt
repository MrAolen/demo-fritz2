package app

import dev.fritz2.core.RenderContext
import dev.fritz2.core.placeholder
import dev.fritz2.core.storeOf
import dev.fritz2.core.value
import dev.fritz2.core.values

fun RenderContext.inputBindingPage() {
  val searchStore = storeOf("")

  div("container mt-3") {
    div("row") {
      div("col-2") {}
      div("col-8") {
        div("input-group") {
          input("form-control") {
            placeholder("Nom du gif à rechercher")
            value(searchStore.data)
            changes.values() handledBy searchStore.update
          }
          button("btn btn-outline-secondary") {
            +"Rechercher"
          }
          button("btn btn-outline-secondary") {
            +"Trump"
            clicks handledBy { searchStore.update("Trump") }
          }
        }
      }
      div("col-2") {}
    }
  }

  div("container mt-3") {
    +"Contenu de la recherche : "
    searchStore.data.render { content ->
      +content
    }
  }
}
