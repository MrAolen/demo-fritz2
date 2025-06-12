package app

import app.model.Gif
import app.model.GiphyResponse
import dev.fritz2.core.RenderContext
import dev.fritz2.core.RootStore
import dev.fritz2.core.placeholder
import dev.fritz2.core.src
import dev.fritz2.core.storeOf
import dev.fritz2.core.value
import dev.fritz2.core.values
import dev.fritz2.remote.decoded
import dev.fritz2.remote.http
import kotlinx.coroutines.Job

fun RenderContext.inputWithApiCallPage() {
  val queryStore = storeOf("")
  val resultGifStore = object : RootStore<List<Gif>>(emptyList(), job = Job()) {
    private val api = http("https://api.giphy.com/v1/gifs")
      .contentType("application/json")

    val searchGif = handle<String> { _, inputSearch ->
      val resp =
        api.get(
          "search",
          mapOf(
            "limit" to 20.toString(),
            "offset" to (1..100).random().toString(),
            "api_key" to "",
            "q" to inputSearch
          )
        )
      require(resp.ok)
      resp.decoded<GiphyResponse>().data
    }
  }

  div("container mt-3") {
    div("row") {
      div("col-2") {}
      div("col-8") {
        div("input-group") {
          input("form-control") {
            value(queryStore.data)
            placeholder("Nom du gif à rechercher")
            changes.values() handledBy queryStore.update
          }
          button("btn btn-outline-secondary") {
            +"Rechercher"
            clicks handledBy { resultGifStore.searchGif(queryStore.current) }
          }
          button("btn btn-outline-secondary") {
            +"Trump"
            clicks handledBy {
              queryStore.update("Trump")
              resultGifStore.searchGif("Trump")
            }
          }
        }
      }
      div("col-2") {}
    }
  }

  div("container mt-3") {
    +"Contenu de la recherche : "
    resultGifStore.data.renderEach { content ->
      img {
        src(content.images.fixed_height.url)
      }
    }
  }
}
