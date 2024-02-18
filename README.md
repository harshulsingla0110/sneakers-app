
# Sneakers App
This is a Sneakers app developed as part of a technical interview assignment. The app allows users to browse a collection of sneakers, view details about each sneaker, add sneakers to a cart, and view their cart.

## Screenshot
<p align="center">
<img width="100%" src="https://i.ibb.co/xFYkvkF/sneaker-cover.png">
</p>


 ## About
- Display a grid of top 100 sneakers with pagination.
- Sneaker details page showing title, name, image, brand, year of release, and price.
- Search from sneakers collection.
- Add sneakers to cart functionality.
- Cart page displaying all added sneakers with image and price.
- Removal of items from the cart.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android](https://developer.android.com/topic/libraries/architecture) Architecture Components - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/training/data-storage/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting ViewModel.
- [Navigation](https://developer.android.com/guide/navigation) - For managing navigation between different screens within a single activity
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - To efficiently load and display large lists of items
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.

## Package Structure
````
com.harshul.shoesapp    # Root Package

├── data                # For data handling
│   ├── db              # Local Persistence Database. Room (SQLite) database
|   │   ├── dao         # Data Access Object for Room  
|   ├── api             # Retrofit API for remote end point
│   ├── di              # Dependency Injection
|   │   ├── module      # DI Modules
│   ├── models          # Model classes
│   ├── pagination      # Pagination source and load adapters
│   └── repos           # Single source of data
|
├── ui                  # Activity/View layer
│   ├── adapter         # Adapter for RecyclerView
│   ├── components      # Jetpack compose components
│   └── view            # Main Screen Activity & ViewModel
|       ├── fragments   # Fragments
|       └── viewmodels  # Viewmodels
│   
└── utils               # Utility Classes / Kotlin extensions
````

## Assumptions and Decisions
**Language Choice :**
 Developed the app using Kotlin as per the preference mentioned in the assignment.

**UI Design :**
 Designed a simple and functional UI with a focus on usability and performance. Used bottom navigation for easy navigation between screens.

**Data Source :**
 Used hardcoded data instead of calling a REST API, as per the assignment instructions.

**Page Size :**
 Set the page size for pagination to 5, considering usability and performance aspects.

 ## Test Cases

**Hilt Modules and Dependencies :**
Included test cases to verify the correctness of Hilt modules and dependencies setup.

**Room Database Operations :**
Added test cases to ensure that Room database operations such as insertion, retrieval, and deletion are working correctly.

Step up your sneaker game with SneakerShip! 👟✨
