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

You need to get an APIKEY from [NewsApi](https://newsapi.org/docs/endpoints/top-headlines)
Replace NEWS_API_KEY in
file [NewsNetworkModule.kt](feature/news/src/main/kotlin/com/instantsystem/android/feature/news/di/NewsNetworkModule.kt)
to be able to load News and run different test.

# TODO

- use io.ktor:ktor-client-mock to mock all server response and tune more all application code
- Check if there is an other way to
  take [default Locale from device](https://developer.android.com/guide/topics/resources/multilingual-support)
- Debug the strange behavior of Flow<PagingData<T>> when screen rotate (new call to server) even if
  it
  cached in viewModel and use internally remember ?!! (My first exprience with Pager 3)
- Enhance more Ui :
    - [Adaptive Ui](https://developer.android.com/develop/ui/compose/layouts/adaptive/build-adaptive-navigation)
    - Move common Composable inside core:designsystem

- Add offline support (database)
- Add check network connectivity
- Add SplashScreen

# Demo

## Handle Server Error Max Request
art/error_max_request_response.mp4

## Handle Server Error No API Key
art/error_no_api_key_server.mp4

## fetch data with pagination
art/fetch_data_using_paggination.mp4