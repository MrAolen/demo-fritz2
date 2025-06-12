package app

import app.model.Gif
import app.model.GiphyResponse
import dev.fritz2.core.RenderContext
import dev.fritz2.core.RootStore
import dev.fritz2.core.disabled
import dev.fritz2.core.placeholder
import dev.fritz2.core.src
import dev.fritz2.core.value
import dev.fritz2.core.values
import dev.fritz2.remote.decoded
import dev.fritz2.remote.http
import dev.fritz2.validation.ValidatingStore
import dev.fritz2.validation.Validation
import dev.fritz2.validation.ValidationMessage
import dev.fritz2.validation.storeOf
import dev.fritz2.validation.validation
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map

enum class Severity(val cssClass: String) {
  Info("primary"),
  Warning("warning"),
  Error("danger")
}

data class Message(override val path: String, val severity: Severity, val text: String) : ValidationMessage {
  override val isError: Boolean = severity > Severity.Warning
}

fun RenderContext.inputWithValidationPage() {

  val validations: Validation<String, Unit, Message> = validation { inspector ->
    val input = inspector.data
    when {
      input.trim().isBlank() -> add(Message(input, Severity.Error, "Please provide a valid input."))
      input.contains("[0-9]".toRegex()) -> add(
        Message(
          input,
          Severity.Warning,
          "Careful, an input with number should not match any gif."
        )
      )

      input == "PSG" -> add(Message(input, Severity.Info, "ICI C'EST PARIS !"))
    }
  }

  val queryStore: ValidatingStore<String, Unit, Message> = storeOf("", validations)
  val resultGifStore = object : RootStore<List<Gif>>(emptyList(), job = Job()) {
    private val api = http("https://api.giphy.com/v1/gifs")
      .contentType("application/json")

    val searchGif = handle<String> { _, inputSearch ->
      val resp =
        api.get(
          "search",
          mapOf(
            "limit" to 20.toString(),
            "offset" to (1..10).random().toString(),
            "api_key" to "",
            "q" to inputSearch
          )
        )
      require(resp.ok)
      resp.decoded<GiphyResponse>().data
    }
  }

  div("container mt-3") {
    queryStore.messages.renderEach {
      val severityName = it.severity.cssClass
      div("p-3 text-$severityName-emphasis bg-$severityName-subtle border border-$severityName-subtle rounded-3") {
        +it.text
      }
    }

    div("row mt-3") {
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

            disabled(queryStore.messages.map { it.any { message -> message.isError } })

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

    div("mt-3") {
      +"Contenu de la recherche : "
    }

    div("mt-3") {
      resultGifStore.data.renderEach { content ->
        img("ms-3 me-3 mt-3") {
          src(content.images.fixed_height.url)
        }
      }
    }
  }
}
