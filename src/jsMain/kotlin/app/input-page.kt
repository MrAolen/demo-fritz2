package app

import dev.fritz2.core.RenderContext
import dev.fritz2.core.placeholder

fun RenderContext.inputPage() {
    div {
        label {
            +"Rechercher un gif"
        }
        input {
            placeholder("Nom du gif")
        }
        button {
            +"Rechercher"
        }
    }
}
