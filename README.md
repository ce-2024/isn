**Instant System news app** is a Android app built entirely with Kotlin and Jetpack Compose.

# Architecture and Libraries

MVVM + Clean architecture

- Dependency injection : Koin (full kotlin library + KMM friendly)
- Networking : ktor (full kotlin library + KMM friendly) and very easy to use
- Compose : for the UI
- Kotlin test: for local unit test
- Androidx Paging 3 : To manage fetching news from the server per page.

# Modularization

    - core::network:

Provide unique Ktor client for all application

    - core::common:

Provide common code like dispatcher and network utilities

    - core::designsystem:

Provide Material design system and common composable for all application other modules

# Features

News: User can fetch top headlines news
using [NewsApi](https://newsapi.org/docs/endpoints/top-headlines) Rest API.

- Isolated feature code
- Manage it s own network stack by extending core network config
- Mvvm + clean architecture
    - data layer : contain main entity class (reponse request from remote server)
        - repository: responsible for fetching data from api server
    - domain layer : contain use case and business logic
        - model: business model , will be shared with ui layer
    - ui layer : ui components
        - communicate with domain layer using viewModels

# Build & Run

You need to get an APIKEY from [newsapi.org](https://newsapi.org/docs/endpoints/top-headlines)

Set the API key in your local.properties file under your root project as follows:

    NEWS_API_KEY=XXXXXXXXXXXXXXXXX

# TODO

- use io.ktor:ktor-client-mock to mock all server response and tune more all application code
- Check if there is an other way to
  take [default Locale from device](https://developer.android.com/guide/topics/resources/multilingual-support)
- Enhance more Ui :
    - [Adaptive Ui](https://developer.android.com/develop/ui/compose/layouts/adaptive/build-adaptive-navigation)
    - Move common Composable inside core:designsystem module

- Add offline support (database)
- Add check network connectivity
- Add SplashScreen

# Demo

## Handle Server Error Max Request

https://github.com/user-attachments/assets/262cf08f-f841-44d0-8c86-15efe97cce2c

## Handle Server Error No API Key

https://github.com/user-attachments/assets/b4688576-c312-4abc-be4b-28ef9a89d043

## fetch data with pagination

https://github.com/user-attachments/assets/c5e1a707-461f-47a5-96e7-97153d5e9946


