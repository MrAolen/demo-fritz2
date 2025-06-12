package app

import dev.fritz2.core.render
import dev.fritz2.routing.routerOf

fun main() {
  val router = routerOf("simple-page")
  render {
    router.data.render { page ->
      when (page) {
        "simple-page" -> simplePage()
        "input-page" -> inputPage()
        "input-page-css" -> inputPageWithCss()
        "input-binding" -> inputBindingPage()
        "input-call-api" -> inputWithApiCallPage()
        "input-validation" -> inputWithValidationPage()
        else -> div { +"ERROR" }
      }
    }
  }
}
