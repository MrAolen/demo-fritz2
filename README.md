[![fritz2](https://www.fritz2.dev/img/fritz2_header.png)](https://www.fritz2.dev/)

Ce repo contient une démonstration du framework fritz2 basé sur Kotlin JS (https://www.fritz2.dev/). 

## Lancer le projet

Cloner le repository et exécuter la commande :

```bash
./gradlew jsRun
```

Vous pouvez aussi activer le live reloading en exécutant la commande suivante :

```bash
./gradlew jsRun -t
```

Vous pourrez alors accéder alors à l'application en vous rendant à l'adresse suivante : http://localhost:8080

## Contenu du repository

Ce projet contient six pages qui sont des exemples d'utilisation de fritz2 :
* http://localhost:8080/#simple-page : contient un affichage HTML simple avec juste une phrase
* http://localhost:8080/#input-page : contient un affichage HTML d'un formulaire de recherche
* http://localhost:8080/#input-page-css : Contient le même affichage que la page précédente mais avec un peu de CSS
* http://localhost:8080/#input-binding : contient une page qui illustre le bidirectionnal binding vu pendant la présentation
* http://localhost:8080/#input-call-api: contient une page avec une recherche API suite à la validation du formulaire
* http://localhost:8080/#input-validation : Contient une page avec une validation de formulaire

