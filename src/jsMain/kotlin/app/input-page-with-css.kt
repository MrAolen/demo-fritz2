package app

import dev.fritz2.core.RenderContext
import dev.fritz2.core.placeholder

fun RenderContext.inputPageWithCss() {
    div("container mt-3") {
        div("row") {
            div("col-2") {}
            div("col-8") {
                div("input-group") {
                    input("form-control") {
                        placeholder("Nom du gif à rechercher")
                    }
                    button("btn btn-outline-secondary") {
                        +"Rechercher"
                    }
                }
            }
            div("col-2") {}
        }
    }
}
