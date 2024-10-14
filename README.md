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

## Handling Server error
<img src="https://github.com/user-attachments/assets/c52b064f-e6f7-409c-9d03-6ad468f7c526" width="280">
<img src="https://github.com/user-attachments/assets/5f1cef0b-5aa7-488d-87db-f13a4cc6d400" width="280">

## Handling Server empty result
<img src="https://github.com/user-attachments/assets/2e0206e6-ed37-4bfc-8d67-e79210764959" width="280">
<img src="https://github.com/user-attachments/assets/dc7abd87-8516-48fe-9d81-91d9a4f7a712" width="280">


## App demo

https://github.com/user-attachments/assets/11e96834-2ee2-4e5a-95df-ad89a1c2f0d7




