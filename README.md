<h1 align="center">Pokedex</h1>
<p align="center">  
Pokedex demonstrates modern Android development with Koin, Coroutines, Flow, Jetpack (Compose, Room, ViewModel), Paging3 and Material Design based on MVVM architecture.

<p align="center">
<img src="/previews/pokedex.jpg"/>
</p>

## Tech stack

- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/)
  based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
  + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous.
- Jetpack
  - Compose: Android’s recommended modern toolkit for building native UI.
  - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive
    configuration changes such as screen rotations.
  - Paging3: Helps you load and display pages of data from a larger dataset from local storage or
    over network.
  - Room: Constructs Database by providing an abstraction layer over SQLite to allow fluent database
    access.
  - [Koin](https://insert-koin.io/): for dependency injection.
  - Navigation: Navigation refers to the interactions that allow users to navigate across, into, and
    back out from the different pieces of content within your app.
- Architecture
  - MVVM Architecture (Model - View - ViewModel)
  - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging
  network data.
- [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html): Serialization is the
  process of converting data used by an application to a format that can be transferred over a
  network or stored in a database or a file.
- [ksp](https://github.com/google/ksp): Kotlin Symbol Processing API.
- [Coil](https://coil-kt.github.io/coil/): An image loading library for Android backed by Kotlin
  Coroutines.
- [Lottie](https://lottiefiles.com/): Lightweight, scalable animations for your apps.
- [Accompanist](https://google.github.io/accompanist/): Accompanist is a group of libraries that aim
  to supplement Jetpack Compose with features that are commonly required by developers but not yet
  available.
- [Palette](https://developer.android.com/reference/androidx/palette/graphics/Palette): A helper
  class to extract prominent colors from an image.

## Architecture

**Pokedex** is based on the MVVM architecture and the Repository pattern, which follows
the [Google's official architecture guidance](https://developer.android.com/topic/architecture).

The overall architecture of **Pokedex** is composed of two layers; the UI layer and the data layer.
Each layer has dedicated components and they have each different responsibilities, as defined below:

**Pokedex** was built
with [Guide to app architecture](https://developer.android.com/topic/architecture), so it would be a
great sample to show how the architecture works in real-world projects.

### Overview

- Each layer
  follows [unidirectional event/data flow](https://developer.android.com/topic/architecture/ui-layer#udf)
  ; the UI layer emits user events to the data layer, and the data layer exposes data as a stream to
  other layers.
- The data layer is designed to work independently from other layers and must be pure, which means
  it doesn't have any dependencies on the other layers.

With this loosely coupled architecture, you can increase the reusability of components and
scalability of your app.

### UI Layer

The UI layer consists of UI composable elements to configure screens that could interact with users
and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that holds app
states and restores data when configuration changes.

### Data Layer

The data Layer consists of the repository, which include business logic, such as querying data from
the local database and requesting remote data from the network. It is implemented as an
offline-first source of business logic and follows
the [single source of truth](https://en.wikipedia.org/wiki/Single_source_of_truth) principle.<br>

**Pokedex** is an offline-first app is an app that is able to perform all, or a critical subset of
its core functionality without access to the internet. So users don't need to be up-to-date on the
network resources every time and it will decrease users' data consumption. For further information,
you can check
out [Build an offline-first app](https://developer.android.com/topic/architecture/data-layer/offline-first)
.

## Open API

<img src="https://user-images.githubusercontent.com/24237865/83422649-d1b1d980-a464-11ea-8c91-a24fdf89cd6b.png" align="right" width="21%"/>

Pokedex using the [PokeAPI](https://pokeapi.co/) for constructing RESTful API.<br>
PokeAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of
data related to Pokémon.

