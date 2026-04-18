# 🤖 Comprehensive Android App Development Roadmap
## Kotlin + Jetpack Compose — From Fundamentals to Production

> **Target Audience:** Students / developers who know programming basics and want to become a professional Android developer.
> **Last Updated:** April 2026

---

## 🗺 Roadmap Overview

```
Phase 1: Kotlin Language              ██████░░░░░░░░░░░░░░  (Weeks 1-3)
Phase 2: Android Fundamentals         ████████░░░░░░░░░░░░  (Weeks 4-6)
Phase 3: Jetpack Compose UI            ██████████░░░░░░░░░░  (Weeks 7-10)
Phase 4: Architecture & State          ████████████░░░░░░░░  (Weeks 11-13)
Phase 5: Data & Networking             ██████████████░░░░░░  (Weeks 14-16)
Phase 6: Firebase & Backend            ████████████████░░░░  (Weeks 17-19)
Phase 7: Advanced Android              ██████████████████░░  (Weeks 20-23)
Phase 8: Testing & Production          ████████████████████  (Weeks 24-26)
```

---

# Phase 1: Kotlin Language Mastery (Weeks 1–3)

> You CANNOT build good Android apps without solid Kotlin. This is the foundation everything else rests on.

## 1.1 — Kotlin Basics
*If you already know Java or any OOP language, this will be quick.*

| Topic | What to Learn | Practice Project |
|---|---|---|
| Variables | `val` vs `var`, type inference, `String`, `Int`, `Double`, `Boolean` | — |
| Null Safety | `?`, `!!`, `?.`, `?:` (Elvis operator), `let`, `also` | — |
| Control Flow | `if/else` as expression, `when` (replaces switch), ranges (`1..10`) | — |
| Functions | Named args, default params, single-expression functions, `Unit` | Simple calculator |
| String Templates | `"Hello, $name"` and `"Result: ${a + b}"` | — |

**Key Concept:** In Kotlin, `if` is an expression (it returns a value), and `when` replaces Java's `switch` entirely. Think in Kotlin, not Java.

## 1.2 — Object-Oriented Kotlin
| Topic | What to Learn |
|---|---|
| Classes | Primary constructors, `init` blocks, properties |
| Data Classes | `data class User(val name: String, val age: Int)` — auto-generates `equals`, `hashCode`, `toString`, `copy` |
| Sealed Classes | `sealed class Result<T>` → `Success`, `Error`, `Loading` — used HEAVILY in Android for UI states |
| Objects | `object` for singletons, `companion object` for static-like members |
| Interfaces | Interface with default implementations |
| Inheritance | `open class`, `abstract class`, `override` |
| Enums | `enum class Direction { NORTH, SOUTH, EAST, WEST }` |

**🔴 Critical for Android:** `data class` and `sealed class` are used in literally every ViewModel and Repository. Master them.

## 1.3 — Functional Kotlin (Collections & Lambdas)
| Topic | What to Learn |
|---|---|
| Lambdas | `{ x: Int -> x * 2 }`, trailing lambda syntax |
| Higher-Order Functions | Functions that accept or return other functions |
| Collection Operations | `.map {}`, `.filter {}`, `.forEach {}`, `.find {}`, `.any {}`, `.groupBy {}` |
| Scope Functions | `let`, `apply`, `run`, `with`, `also` — know when to use each |
| Extension Functions | `fun String.isEmailValid(): Boolean` — adding functions to existing classes |

**Practice Project:** Build a TODO list in the terminal using `data class Task(...)`, `mutableListOf`, `.filter`, `.map`, and `.sortedBy`.

## 1.4 — Coroutines & Flow (Asynchronous Kotlin)
*This is the MOST IMPORTANT Kotlin topic for Android development.*

| Topic | What to Learn | Why It Matters |
|---|---|---|
| Basics | `suspend` functions, `launch`, `async/await` | Every network/database call is a coroutine |
| Coroutine Scope | `viewModelScope`, `lifecycleScope`, `GlobalScope` (never use) | Controls when coroutines get cancelled |
| Dispatchers | `Dispatchers.Main`, `.IO`, `.Default` | Main = UI thread, IO = network/disk, Default = CPU work |
| `Flow` | Cold streams, `.collect {}`, `.map {}`, `.filter {}` | Firestore listeners, location updates |
| `StateFlow` | Hot stream, `.value`, `MutableStateFlow` | ViewModel → UI state communication |
| `SharedFlow` | Hot stream for events, replay cache | One-time events like navigation or toasts |
| `callbackFlow` | Converting callback APIs to Flows | GPS updates, Firestore snapshot listeners |
| Exception Handling | `try/catch` in coroutines, `CoroutineExceptionHandler` | Graceful error handling |

**Practice Project:** Create a countdown timer using `Flow` that emits values every second.

**📖 Resources:**
- [Kotlin Coroutines Official Guide](https://kotlinlang.org/docs/coroutines-guide.html)
- YouTube: Philipp Lackner — "Kotlin Coroutines Full Course"

---

# Phase 2: Android Platform Fundamentals (Weeks 4–6)

> Before touching Compose, understand the platform you're building on.

## 2.1 — Android Project Structure
| Topic | What to Learn |
|---|---|
| Project Layout | `app/src/main/java`, `res/`, `AndroidManifest.xml` |
| Build System | `build.gradle.kts`, Version Catalog (`libs.versions.toml`), `compileSdk`, `minSdk`, `targetSdk` |
| Manifest | Declaring activities, services, permissions, intent filters |
| Resources | `strings.xml`, `colors.xml`, `themes.xml`, drawable resources |
| Signing | Debug keystore vs release keystore, generating a signed APK |

## 2.2 — Activity & Lifecycle
| Topic | What to Learn |
|---|---|
| Activity | `ComponentActivity`, `setContent {}` for Compose |
| Lifecycle States | `Created → Started → Resumed → Paused → Stopped → Destroyed` |
| Configuration Changes | Screen rotation, dark mode toggle → why ViewModel survives but Activity doesn't |
| Process Death | When the OS kills your app in background → save & restore state |

## 2.3 — Android Core Concepts
| Topic | What to Learn |
|---|---|
| Context | `ApplicationContext` vs `ActivityContext` — when to use which |
| Intents | Explicit (launch a specific Activity) vs Implicit (share, open URL, camera) |
| Permissions | Runtime permissions model (Android 6+), requesting at runtime, handling denial |
| App Shortcuts & Deep Links | Basic understanding for navigation |

## 2.4 — Gradle & Dependency Management
| Topic | What to Learn |
|---|---|
| Version Catalog | `libs.versions.toml` — the modern way to manage dependencies |
| Plugins | `plugins { alias(libs.plugins.xxx) }` |
| Build Variants | `debug` vs `release`, `BuildConfig`, ProGuard/R8 |
| KSP vs KAPT | KSP is the modern annotation processor (Hilt, Room). Always prefer KSP over KAPT. |

**📖 Resources:**
- [Android Developers: App Fundamentals](https://developer.android.com/guide/components/fundamentals)
- Book: "Head First Android Development" (latest edition)

---

# Phase 3: Jetpack Compose UI Toolkit (Weeks 7–10)

> This is where you build visual interfaces. Compose replaces XML layouts entirely.

## 3.1 — Compose Basics
| Topic | What to Learn |
|---|---|
| `@Composable` | Functions that describe UI, not classes |
| Basic Composables | `Text`, `Image`, `Icon`, `Button`, `TextField`, `OutlinedTextField` |
| Layouts | `Column`, `Row`, `Box`, `Spacer`, `Surface` |
| Modifiers | `.fillMaxSize()`, `.padding()`, `.size()`, `.clickable {}`, `.background()`, `.clip()` |
| Modifier Chaining | Order matters! `.padding().background() ≠ .background().padding()` |
| Preview | `@Preview` annotation to see your UI without running the app |

**Practice:** Build a simple profile card with an avatar, name, bio, and a button.

## 3.2 — Material 3 Design System
| Topic | What to Learn |
|---|---|
| Theme Setup | `MaterialTheme { }`, `darkColorScheme()`, `lightColorScheme()`, `dynamicDarkColorScheme()` |
| Color System | `primary`, `onPrimary`, `surface`, `surfaceVariant`, `tertiary`, `error` |
| Typography | `Typography(headlineLarge = TextStyle(...))`, Google Fonts integration |
| Shapes | `RoundedCornerShape`, `shape = MaterialTheme.shapes.medium` |
| Components | `Scaffold`, `TopAppBar`, `NavigationBar`, `FloatingActionButton`, `Card`, `Chip`, `Switch`, `Slider` |
| Bottom Sheets | `ModalBottomSheet`, `rememberModalBottomSheetState()` |
| Dialogs | `AlertDialog`, `DatePickerDialog`, `TimePickerDialog` |

**🔴 Critical:** Always use `MaterialTheme.colorScheme.xxx` for colors, never hardcode. This makes dark mode work automatically.

## 3.3 — Lists & Lazy Composables
| Topic | What to Learn |
|---|---|
| `LazyColumn` | The RecyclerView replacement for vertical lists |
| `LazyRow` | Horizontal scrollable lists |
| `LazyVerticalGrid` | Grid layouts |
| `items()` / `itemsIndexed()` | Populating lists from data |
| `key` | Providing stable keys for efficient recomposition |
| Sticky Headers | `stickyHeader {}` for grouped lists |
| Pull-to-Refresh | `pullToRefresh` modifier |

**Practice:** Build a contact list app: Scrollable list of cards, a search bar at the top that filters the list, and a FAB.

## 3.4 — State Management in Compose
| Topic | What to Learn |
|---|---|
| `remember` | Remembering a value across recompositions |
| `mutableStateOf` | Making a value observable by Compose |
| `rememberSaveable` | Surviving configuration changes (screen rotation) |
| State Hoisting | Moving state UP and events DOWN. The composable doesn't own state, it receives it. |
| `derivedStateOf` | Computing state from other states efficiently |
| Recomposition | Understanding WHEN and WHY Compose re-renders. Avoid unnecessary recompositions. |

**Key Principle:** Compose follows **unidirectional data flow (UDF)**:
```
State flows DOWN (ViewModel → Composable)
Events flow UP (Composable → ViewModel via callbacks)
```

## 3.5 — Animations in Compose
| Topic | What to Learn |
|---|---|
| `AnimatedVisibility` | Show/hide with fade, slide, expand animations |
| `animateContentSize` | Smoothly expanding/collapsing containers |
| `animateFloatAsState` | Animating numeric values |
| `Crossfade` | Smooth content switching |
| `updateTransition` | Complex, multi-property animations |
| `InfiniteTransition` | Looping animations (shimmer effects, loading) |

## 3.6 — Navigation in Compose
| Topic | What to Learn |
|---|---|
| `NavHost` | Setting up the navigation graph |
| `NavController` | `navigate()`, `popBackStack()`, `popUpTo()` |
| Routes | String-based routes with arguments: `"profile/{userId}"` |
| Arguments | `navArgument("userId") { type = NavType.StringType }` |
| Deep Links | Opening a specific screen from a URL |
| Nested Navigation | Bottom navigation with separate nav graphs |
| Back Stack Management | `popUpTo`, `inclusive`, `launchSingleTop` |

**Practice Project:** Build a multi-screen note-taking app with: Login → Notes List → Note Detail → Create Note. Use a `BottomNavigationBar` with 2 tabs.

**📖 Resources:**
- [Jetpack Compose Official Pathway](https://developer.android.com/courses/jetpack-compose/course)
- [Compose Material 3 Catalog App](https://play.google.com/store/apps/details?id=androidx.compose.material.catalog)
- YouTube: Philipp Lackner — Compose playlists

---

# Phase 4: App Architecture & Dependency Injection (Weeks 11–13)

> Writing code that scales and doesn't become an unmaintainable mess.

## 4.1 — MVVM Architecture
| Layer | Responsibility | Android Equivalent |
|---|---|---|
| **View** | Display UI, handle user input | Composable functions |
| **ViewModel** | Hold UI state, handle business logic | `ViewModel` class + `StateFlow` |
| **Model** | Data sources (network, database) | Repository + Data Source classes |

**Data Flow:**
```
Repository ──(suspend/Flow)──▶ ViewModel ──(StateFlow)──▶ Composable
     ◀──────(function call)─────     ◀─────(event callback)────
```

## 4.2 — Clean Architecture (Optional but Recommended)
| Layer | Packages | Contains |
|---|---|---|
| Presentation | `presentation/` | Screens, ViewModels, UI state classes |
| Domain | `domain/` | Models (data classes), Repository interfaces, UseCases |
| Data | `data/` | Repository implementations, API services, DAOs, Firebase calls |
| Core | `core/` | Theme, utils, constants, common sealed classes |

**Why:** Domain layer has ZERO Android dependencies. It can be unit tested without emulators.

## 4.3 — Dependency Injection with Dagger Hilt
| Topic | What to Learn |
|---|---|
| Setup | `@HiltAndroidApp` on Application, `@AndroidEntryPoint` on Activity/Fragment/Service |
| ViewModel Injection | `@HiltViewModel` + `@Inject constructor(...)` |
| Modules | `@Module @InstallIn(SingletonComponent::class) object AppModule` |
| `@Provides` | Creating instances: `fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()` |
| `@Binds` | Binding interfaces to implementations: `abstract fun bindAuthRepo(impl: AuthRepoImpl): AuthRepo` |
| Scoping | `@Singleton` for app-wide, `@ViewModelScoped` for ViewModel lifecycle |
| Assisted Inject | Passing runtime parameters to injected classes |

**Practice:** Refactor your note-taking app to use Hilt. Inject a `NoteRepository` into `NoteViewModel`.

## 4.4 — ViewModel Patterns
| Pattern | What It Solves |
|---|---|
| `sealed interface UiState` | Representing Loading / Success / Error states |
| `data class ScreenUiState(...)` | Holding all UI fields in one immutable object |
| `Channel<UiEvent>` | One-time events (navigation, toasts, snackbars) that shouldn't survive recomposition |
| `sealed class UiEvent` | Type-safe events: `Navigate(route)`, `ShowToast(message)`, `ShowSnackbar(message)` |
| `.update {}` | Atomic state updates: `_state.update { it.copy(isLoading = true) }` |

**📖 Resources:**
- [Android: Guide to App Architecture](https://developer.android.com/topic/architecture)
- YouTube: Philipp Lackner — "Clean Architecture MVVM"

---

# Phase 5: Local Data & Networking (Weeks 14–16)

> Storing data locally and fetching from the internet.

## 5.1 — Room Database (Local SQLite)
| Topic | What to Learn |
|---|---|
| Entity | `@Entity data class Note(@PrimaryKey val id: Int, val title: String)` |
| DAO | `@Dao interface NoteDao { @Query("SELECT * FROM note") fun getAll(): Flow<List<Note>> }` |
| Database | `@Database(entities = [Note::class], version = 1) abstract class AppDatabase` |
| Migrations | `Migration(1, 2)` when you change the schema |
| Relations | `@Embedded`, `@Relation` for one-to-many |
| Flow Integration | DAO returning `Flow<List<T>>` for reactive UI updates |

## 5.2 — DataStore (Key-Value Preferences)
| Topic | What to Learn |
|---|---|
| Preferences DataStore | Replacing `SharedPreferences` — async, safe, coroutine-based |
| Keys | `booleanPreferencesKey("dark_mode")`, `stringPreferencesKey("user_id")` |
| Reading | `dataStore.data.map { prefs -> prefs[KEY] }` returns a `Flow` |
| Writing | `dataStore.edit { prefs -> prefs[KEY] = value }` is a `suspend` function |

## 5.3 — Retrofit & REST API Networking
| Topic | What to Learn |
|---|---|
| Retrofit Setup | `Retrofit.Builder().baseUrl().addConverterFactory(GsonConverterFactory)` |
| API Interface | `@GET("users/{id}") suspend fun getUser(@Path("id") id: String): User` |
| Interceptors | `OkHttpClient` with logging interceptor for debugging |
| Error Handling | Wrapping API calls in `try/catch`, returning `Resource<T>` |
| Pagination | `Paging 3` library for infinite scrolling |

## 5.4 — Image Loading
| Topic | What to Learn |
|---|---|
| Coil | `AsyncImage(model = url, contentDescription = ...)` — the standard for Compose |
| Placeholder & Error | `placeholder(R.drawable.loading)`, `error(R.drawable.broken_image)` |
| Caching | Coil handles disk + memory caching automatically |
| Transformations | Circle crop, rounded corners |

## 5.5 — Offline-First Strategy
| Topic | What to Learn |
|---|---|
| Pattern | Fetch from cache (Room) → Show UI → Fetch from network → Update cache → UI auto-updates via Flow |
| Single Source of Truth | Room database is the truth. Network is just a way to update Room. |

**Practice Project:** Build a GitHub profile viewer: Enter a username → Fetch from GitHub API via Retrofit → Show repos in a list → Cache in Room → Works offline.

**📖 Resources:**
- [Room Official Docs](https://developer.android.com/training/data-storage/room)
- [Retrofit Official Docs](https://square.github.io/retrofit/)

---

# Phase 6: Firebase & Backend-as-a-Service (Weeks 17–19)

> When you don't want to build your own server.

## 6.1 — Firebase Authentication
| Topic | What to Learn |
|---|---|
| Email/Password | `createUserWithEmailAndPassword()`, `signInWithEmailAndPassword()` |
| Google Sign-In | Credential Manager API (new) — replaces legacy Google Sign-In |
| Auth State | `FirebaseAuth.AuthStateListener` → observe login/logout changes |
| `.await()` | `kotlinx-coroutines-play-services` — convert Firebase Tasks to suspend functions |

## 6.2 — Cloud Firestore (NoSQL Database)
| Topic | What to Learn |
|---|---|
| Documents & Collections | `collection("users").document("userId")` |
| CRUD | `.set()`, `.get()`, `.update()`, `.delete()` — all with `.await()` |
| Queries | `.whereEqualTo()`, `.orderBy()`, `.limit()` |
| Real-time Listeners | `.addSnapshotListener {}` → wrapped in `callbackFlow` for Kotlin Flow integration |
| Subcollections | Nested data: `groups/{id}/members/{userId}` |
| Data Modeling | Denormalization, embedding vs referencing, array fields vs subcollections |
| Batch Writes | `firestore.runBatch { }` for atomic multi-document operations |
| Security Rules | `request.auth.uid`, `resource.data`, read/write rules per collection |

## 6.3 — Firebase Cloud Storage
| Topic | What to Learn |
|---|---|
| Upload | `storageRef.child("profiles/$userId.jpg").putFile(uri)` |
| Download URL | `storageRef.downloadUrl.await()` → store the URL in Firestore |
| Progress | `addOnProgressListener { snapshot -> val progress = snapshot.bytesTransferred }` |

## 6.4 — Firebase Cloud Messaging (FCM)
| Topic | What to Learn |
|---|---|
| Token Registration | `FirebaseMessaging.getInstance().token.await()` → save to Firestore |
| Receiving Messages | `FirebaseMessagingService.onMessageReceived()` |
| Notification Channels | Required for Android 8+ |
| Topics | Subscribe users to group-based topics |

## 6.5 — Firebase Crashlytics & Analytics
| Topic | What to Learn |
|---|---|
| Crashlytics | Automatic crash reporting with stack traces |
| Analytics | `FirebaseAnalytics.logEvent()` for tracking user behavior |
| Performance | Basic performance monitoring |

**Practice Project:** Build a real-time chat app: Firebase Auth → Firestore messages → Real-time listener → Send/receive messages → Profile pictures in Cloud Storage.

**📖 Resources:**
- [Firebase for Android Docs](https://firebase.google.com/docs/android/setup)
- [Firebase Kotlin Extensions](https://firebase.google.com/docs/android/kotlin-extensions)

---

# Phase 7: Advanced Android Topics (Weeks 20–23)

> Features that separate hobby apps from production apps.

## 7.1 — Background Work
| Topic | When to Use |
|---|---|
| Foreground Service | Continuous tasks the user is aware of (music, location tracking, downloads) |
| WorkManager | Deferred, guaranteed tasks (sync data, upload photos, daily backups) |
| AlarmManager | Exact-time scheduling (reminders, alarms) |

**Foreground Service Deep-Dive:**
| Topic | What to Learn |
|---|---|
| Service Class | `class MyService : Service()`, `onStartCommand()`, `onDestroy()` |
| Notification | Must show a persistent notification via `startForeground()` |
| Service Types | `android:foregroundServiceType="location"` / `"mediaPlayback"` / `"dataSync"` |
| Permissions | `FOREGROUND_SERVICE`, `FOREGROUND_SERVICE_LOCATION` (Android 14+) |
| Communication | Use `Flow` or `BroadcastReceiver` to send data from Service to UI |

## 7.2 — Location & Maps
| Topic | What to Learn |
|---|---|
| FusedLocationProvider | `getCurrentLocation()`, `requestLocationUpdates()` |
| Location Permissions | Fine vs Coarse, Background location (separate request on Android 11+) |
| Google Maps Compose | `GoogleMap`, `Marker`, `Polyline`, `CameraPositionState` |
| Geofencing | Trigger actions when user enters/exits a geographic area |

## 7.3 — Notifications
| Topic | What to Learn |
|---|---|
| Notification Channels | Grouping notifications by type |
| NotificationCompat | Building rich notifications with actions, images, progress |
| Pending Intents | Launching activities or services from notification taps |
| Big Picture / Big Text | Expanded notification styles |
| POST_NOTIFICATIONS | Runtime permission on Android 13+ |

## 7.4 — App Widgets (Glance)
| Topic | What to Learn |
|---|---|
| Glance API | Compose-like API for home screen widgets |
| GlanceAppWidget | Defining widget content using Glance composables |
| GlanceAppWidgetReceiver | Receiving widget updates |
| Worker Integration | Refreshing widget data with WorkManager |

## 7.5 — Camera & Media
| Topic | What to Learn |
|---|---|
| CameraX | Taking photos, recording video with Compose integration |
| Photo Picker | `ActivityResultContracts.PickVisualMedia()` — modern image picker |
| File Provider | Sharing files securely between apps |

## 7.6 — Security
| Topic | What to Learn |
|---|---|
| ProGuard / R8 | Code obfuscation and shrinking for release builds |
| Network Security Config | Restricting cleartext traffic, certificate pinning |
| Encrypted DataStore | Storing sensitive data securely |
| API Key Protection | Never hardcode API keys in source code. Use `local.properties` + `BuildConfig`. |

**📖 Resources:**
- [Android: Background Work Guide](https://developer.android.com/develop/background-work)
- [Google Maps Compose Library](https://github.com/googlemaps/android-maps-compose)

---

# Phase 8: Testing & Production Readiness (Weeks 24–26)

> Shipping a reliable, crash-free app.

## 8.1 — Unit Testing
| Topic | What to Learn |
|---|---|
| JUnit 5 | `@Test`, `assertEquals()`, `assertThrows()` |
| Mocking | MockK library — `mockk<AuthRepository>()`, `coEvery { }`, `verify { }` |
| Testing Coroutines | `runTest { }`, `TestDispatcher`, `advanceUntilIdle()` |
| Testing ViewModels | Inject mocked repositories, assert StateFlow values |
| Testing UseCases | Pure Kotlin unit tests — no Android framework needed |

## 8.2 — UI Testing
| Topic | What to Learn |
|---|---|
| Compose Testing | `createComposeRule()`, `onNodeWithText("Login").performClick()` |
| Semantics | `Modifier.semantics {}`, `testTag("login_button")`, `onNodeWithTag()` |
| Assertions | `.assertIsDisplayed()`, `.assertTextEquals()`, `.assertIsEnabled()` |
| Navigation Testing | `TestNavHostController` for verifying navigation routes |

## 8.3 — Performance
| Topic | What to Learn |
|---|---|
| Compose Compiler Metrics | Detecting unstable classes causing unnecessary recompositions |
| Baseline Profiles | Pre-compiling frequently used code paths for faster startup |
| LeakCanary | Detecting memory leaks automatically |
| Strict Mode | Detecting accidental disk/network access on the main thread |

## 8.4 — Release & Publishing
| Topic | What to Learn |
|---|---|
| Signing Config | Generating release keystore, storing securely |
| Build Types | `debug` vs `release`, enabling R8 minification |
| App Bundle | `.aab` format for Google Play (smaller downloads) |
| Play Console | Creating a listing, uploading AAB, managing release tracks (internal → beta → production) |
| Version Management | `versionCode` (incremental integer) and `versionName` (user-facing string) |

---

# 🎓 Recommended Learning Resources (Curated)

## Video Courses
| Resource | Best For |
|---|---|
| [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course) | Official Google course — best starting point |
| Philipp Lackner (YouTube) | Clean Architecture, MVVM, Hilt, Compose deep-dives |
| Coding With Mitch (YouTube) | Complex project builds, advanced architecture |
| Stevdza-San (YouTube) | Quick Compose component tutorials |

## Documentation
| Resource | Link |
|---|---|
| Kotlin Official | [kotlinlang.org/docs](https://kotlinlang.org/docs/home.html) |
| Android Developers | [developer.android.com](https://developer.android.com/develop) |
| Compose API Reference | [developer.android.com/reference/kotlin/androidx/compose](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary) |
| Material 3 Guidelines | [m3.material.io](https://m3.material.io/) |
| Firebase Android | [firebase.google.com/docs/android](https://firebase.google.com/docs/android/setup) |

## Books
| Book | Focus Area |
|---|---|
| Kotlin in Action (2nd Edition) | Deep Kotlin language mastery |
| Head First Android Development | Android fundamentals (beginner-friendly) |
| Jetpack Compose Internals | How Compose actually works under the hood (advanced) |

---

# 🏗 Practice Project Ideas (By Difficulty)

## 🟢 Beginner (After Phase 3)
1. **Tip Calculator** — TextField + calculations + M3 styling
2. **Unit Converter** — Dropdown + conversion logic + animations
3. **Quote of the Day App** — Static list + Card UI + random quote

## 🟡 Intermediate (After Phase 5)
4. **Expense Tracker** — Room DB + Form UI + Charts + Category filtering
5. **Weather App** — Retrofit API call + Location + LazyColumn + Icons
6. **Note App with Tags** — Room + Search + Filter + Multi-select delete

## 🔴 Advanced (After Phase 7)
7. **Real-Time Chat App** — Firebase Auth + Firestore + FCM + Typing indicators
8. **Fitness Tracker** — Foreground Service + Step counter sensor + Room + Charts
9. **Location Sharing App** — Maps + Live location + Foreground Service + Groups (This is your NearBy!)

---

# 📋 Technology Cheat Sheet

Quick reference of the current recommended libraries (as of 2026):

| Category | Library | Purpose |
|---|---|---|
| UI | Jetpack Compose + Material 3 | Declarative UI toolkit |
| Navigation | Navigation Compose | Screen navigation |
| DI | Dagger Hilt | Dependency injection |
| Async | Kotlin Coroutines + Flow | Asynchronous programming |
| Local DB | Room | SQLite ORM |
| Preferences | DataStore | Key-value storage |
| Networking | Retrofit + OkHttp + Gson/Moshi | REST API calls |
| Images | Coil | Image loading & caching |
| Maps | Maps Compose | Google Maps UI |
| Location | Play Services Location | GPS access |
| Auth | Firebase Auth | User authentication |
| Cloud DB | Cloud Firestore | Real-time NoSQL DB |
| Push Notifications | Firebase Cloud Messaging | Remote notifications |
| Background | WorkManager | Deferred background tasks |
| Background (live) | Foreground Service | Continuous background tasks |
| Logging | Timber | Better than `Log.d()` |
| Testing | JUnit + MockK + Compose Testing | Unit & UI tests |
| Crash Reporting | Firebase Crashlytics | Production crash tracking |
| Annotation Processing | KSP | Modern replacement for KAPT |

---

> **Final Advice:** Don't try to learn everything before building. Learn a phase, build something small, then move to the next phase. The best developers learn by shipping, not by watching tutorials forever.
