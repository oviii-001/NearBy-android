# 📍 NearBy - Detailed Development & Learning Roadmap

Welcome to the deep-dive roadmap for **NearBy**. Building an app with real-time location and maps can be tricky, so this document breaks down the entire process into microscopic steps. 

For each phase, you will see exactly **What to Learn**, the **Architecture/Tech**, and the **Step-by-Step Implementation**.

---

## 🗓 The Exact Coding Sequence
*If you are wondering "What file should I open right now?", follow this exact chronological sequence. Check these off as you code them.*

1. **[x] Define Packages:** Create your folders (`di`, `domain`, `presentation`, `data`, `core`).
2. **[ ] App & Dagger Hilt Setup:** Write `NearByApplication.kt` + `@HiltAndroidApp` -> Register in Manifest -> Create empty `AppModule.kt`. 
3. **[ ] Theming & Styling:** Fill in `Color.kt`, `Theme.kt`, `Type.kt` using your Material 3 design rules.
4. **[ ] Empty Screen Skeletons:** Write blank composable functions for `SplashScreen`, `LoginScreen`, `RegisterScreen`, `HomeScreen`.
5. **[ ] Navigation Foundation:** Code `Screen.kt` (routes) -> Code `AppNavGraph.kt` (`NavHost`) -> Wire `MainActivity` to start `AppNavGraph`.
6. **[ ] Splash Flow:** Design `SplashScreen` UI -> Add a 2s delay in `LaunchedEffect` -> Navigate to Login.
7. **[ ] Session State (Local DB):** Setup `UserPreferences.kt` (DataStore) to hold boolean `isLoggedIn`. Update Splash: check DataStore -> if logged in, go to Home; if not, go to Login.
8. **[ ] Auth Layer (Firebase):** Connect Firebase Console -> Code `AuthRepository` & `AuthRepositoryImpl` -> Wire to `LoginViewModel`. If login succeeds, update DataStore `isLoggedIn = true` and jump to Home.
9. **[ ] Dashboard/Home UI:** Code `HomeScreen.kt` with a TopBar and an empty list. Add a Floating Action Button for creating a group.
10. **[ ] Group Logic (Firestore):** Build "Create Group" dialog UI -> Code `GroupRepositoryImpl` to push group to Firestore -> Reload Home list.
11. **[ ] Location Permissions:** User clicks a group -> goto `MapScreen` -> Immediately show an Android permission dialog for Fine/Coarse Location.
12. **[ ] Standard Map View:** Add `GoogleMap` composable. Get a one-time location from `FusedLocationProviderClient` and center the camera on the user.
13. **[ ] The Background Engine:** Code the Android Foreground Service (`LocationTrackingService.kt`) with a persistent notification. Have it stream location updates to Firestore `live_locations` subcollection.
14. **[ ] Multi-user Rendering:** In `MapViewModel`, listen to the `live_locations` Firestore collection. Update the `GoogleMap` UI with markers for everyone else in the group.
15. **[ ] ETA Math:** Calculate `Location.distanceTo` for other users relative to a Meetup Point. Divide by speed to get ETA. Add this to a bottom sheet list on the `MapScreen`.
16. **[ ] Polish:** Push Notifications via FCM for "User has arrived".

---

## ✅ Phase 1: The Foundation (Architecture & Navigation) [COMPLETED]
*Learn how to structure a large app so it doesn't become a mess of spaghetti code.*

### 📚 What to Learn
* **Clean Architecture layers:** Presentation (UI), Domain (Business Logic/Models), Data (Network/Database).
* **Dagger Hilt:** The standard Dependency Injection framework for Android.
* **Jetpack Compose Navigation:** How to navigate between screens using routes.

### 🛠 Implementation Steps
1. **[x] Define Your Packages:**
   * Create folders: `com.ovi.nearby.di`, `com.ovi.nearby.presentation`, `com.ovi.nearby.domain`, `com.ovi.nearby.data`.
2. **[x] Setup Dagger Hilt:**
   * Create an `Application` class (e.g., `NearByApp: Application()`) and annotate it with `@HiltAndroidApp`.
   * Register `NearByApp` in your `AndroidManifest.xml` inside `<application android:name=".NearByApp" ...>`.
   * Create an object module in your `di` package: `@Module @InstallIn(SingletonComponent::class) object AppModule { ... }`.
3. **[x] Setup Navigation:**
   * In your `MainActivity.kt`, wrap your core UI in a `NavHost`.
   * Define routes: `LoginScreen`, `SignupScreen`, `DashboardScreen`, `MapScreen`.

---

## 🔐 Phase 2: Firebase Auth & User Profiles
*Get users into the app and store their basic identity.*

### 📚 What to Learn
* **Firebase Authentication:** Email/Password implementation.
* **Coroutines with Firebase:** `Task.await()` to transform Firebase callbacks into suspend functions.
* **StateFlow:** Managing UI states (e.g. Loading, Success, Error).

### 🛠 Implementation Steps
1. **Firebase Setup:** Go to Firebase Console, add your Android project, download `google-services.json`, and enable Authentication (Email/password) and Firestore.
2. **Build the UI (Presentation):**
   * Create `LoginScreen.kt` using Material 3 `OutlinedTextField` and `Button`.
3. **Build the Logic (Data & Domain):**
   * Create an `AuthRepository` interface (Domain) and `AuthRepositoryImpl` (Data).
   * Inject `FirebaseAuth` into `AuthRepositoryImpl` using Hilt.
   * Write a suspend function `signIn(email, password)` that calls `firebaseAuth.signInWithEmailAndPassword(...).await()`.
4. **Firestore Profile:**
   * Upon successful signup, write a document to Firestore: Collection `users` -> Document `userId` -> Data: `{ name: "Ovi", email: "...", currentGroupId: null }`.

---

## 👥 Phase 3: Group Management & NoSQL Schema
*Allow users to create and join tracking sessions.*

### 📚 What to Learn
* **Firestore Data Modeling:** Denormalization and subcollections in NoSQL.
* **Kotlin Data Classes:** Modeling your Firestore documents accurately.

### 🛠 Implementation Steps
1. **Design the Schema:**
   * **Collection `groups`**:
      * Document `groupId`:
         * `name`: "Weekend Trip"
         * `adminId`: "userId_of_creator"
         * `members`: ["userId1", "userId2"] *(Array of user IDs)*
         * `destination`: GeoPoint(lat, lng) *(Optional)*
2. **Create Group Logic:**
   * Build a `CreateGroupDialog` composable.
   * On submit, generate a random 6-character code (e.g., `B7X9L2`) or a UUID as the `groupId`.
   * Save the group to the `groups` collection and update the creator's `users` document to reflect their current group.
3. **Join Group Logic:**
   * A simple `TextField` where a user inputs the 6-character code.
   * Query Firestore: Get document by `groupId`. If exists, append the current user's ID to the `members` array using `FieldValue.arrayUnion(userId)`.

---

## 🧭 Phase 4: Permissions & Device Location
*The prerequisite to tracking: asking Android politely for the user's location.*

### 📚 What to Learn
* **Android Permission System:** Coarse vs. Fine vs. Background location.
* **Compose Permissions:** `rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions())`.
* **FusedLocationProviderClient:** Getting a one-time GPS fix.

### 🛠 Implementation Steps
1. **Update Manifest:**
   * Add `<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />`
   * Add `<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />`
2. **Request Permissions in Compose:**
   * Create a button "Enable Location". When clicked, launch the permission request.
   * Check if permissions are granted. If they are permanently denied, direct the user to app settings.
3. **Get One-Time Location:**
   * Inject `FusedLocationProviderClient` via Hilt.
   * Call `fusedLocationClient.lastLocation.await()` to check if the location works locally.

---

## 🛰 Phase 5: The Tracking Engine (Foreground Services & Flows)
*This is the core of the app: streaming location continually to Firebase without the OS killing your app.*

### 📚 What to Learn
* **Foreground Services:** Running background tasks strictly with a persistent notification.
* **Notification Channels:** Required for Android 8+.
* **callbackFlow:** Transforming location callback updates into a Kotlin Flow.

### 🛠 Implementation Steps
1. **Background Permissions (Android 14+ specific):**
   * Add `<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />`
   * Add `<uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />`
2. **Create the Service:**
   * Create a class `LocationTrackingService : Service()`.
   * In `onCreate()`, create a Notification Channel and call `startForeground(ID, notification)`.
3. **The Location Flow (`callbackFlow`):**
   * Write a function that creates a `LocationRequest` (Interval: 10 seconds, Min Update Distance: 5 meters, Priority: High Accuracy).
   * Inside a `callbackFlow`, call `requestLocationUpdates()`. When the callback triggers, use `trySend(location)`.
   * On `awaitClose { ... }`, remove the location updates.
4. **Pushing to Firestore:**
   * Inside the `LocationTrackingService`, collect the `callbackFlow`.
   * On every new location emission, write to Firestore: 
     Collection `groups` -> Document `groupId` -> Subcollection `live_locations` -> Document `userId` -> Data: `{ lat, lng, speed, timestamp }`.

---

## 🗺 Phase 6: Interactive Real-Time Maps
*Seeing everyone moving live on the map UI.*

### 📚 What to Learn
* **Google Maps Jetpack Compose (`maps-compose`):** Adding the map, controlling the camera.
* **Firestore Realtime Listeners:** `addSnapshotListener` to get data the millisecond it changes.

### 🛠 Implementation Steps
1. **Google Cloud Console:** 
   * Enable Maps SDK for Android, get an API Key, and put it in `local.properties`.
2. **The Map Composable:**
   * In your `MapScreen.kt`, add the `GoogleMap { ... }` composable.
3. **Listen to Everyone's Location:**
   * In your ViewModel, create a Kotlin Flow using Firestore's `snapshotListener` targeting the `groups/groupId/live_locations` subcollection.
   * Every time any user's location updates, this flow will emit a new list of locations. Feed this to your UI via a `StateFlow`.
4. **Plot Markers:**
   * Inside the `GoogleMap` block, loop through your `StateFlow` list and render a `Marker` for each user.
   * Pass their profile picture URL to the marker icon for a personalized touch.

---

## ⏱ Phase 7: Math, ETA, and Destinations
*Displaying how far everyone is from the goal.*

### 📚 What to Learn
* **Location calculations:** `Location.distanceTo()`.
* **Basic Physics:** Time = Distance / Speed.

### 🛠 Implementation Steps
1. **Set Destination:** 
   * Allow Admin to tap on the map to set a marker. Save this `GeoPoint` to the Group's main Firestore document.
2. **Calculate Distance:** 
   * In your ViewModel, whenever a user's location updates, create a local `Location` object for their coordinates and the destination coordinates.
   * `val distanceMeters = userLoc.distanceTo(destLoc)`.
3. **Calculate ETA:**
   * `val speedMps = userLoc.speed // meters per second`
   * `if (speedMps > 0) ETA_seconds = distanceMeters / speedMps`
   * Format this beautifully (e.g., "Arriving in 12 mins").
4. **BottomSheet UI:**
   * Add a `ModalBottomSheet` displaying a list of all members, their distances, and their calculated ETAs.

---

## 🔋 Phase 8: Durations, Battery, & Notifications
*Polishing it up for production.*

### 📚 What to Learn
* **Coroutines / Service Lifecycle:** Stopping services automatically.
* **Firebase Cloud Messaging (FCM):** Notifying offline users.

### 🛠 Implementation Steps
1. **Sharing Duration Limitation (15m, 1h):**
   * When starting the `LocationTrackingService`, pass an Intent extra containing the selected duration in milliseconds.
   * Inside the service, launch a coroutine: `delay(durationMs)`, then call `stopSelf()` explicitly. 
2. **Arrival Logic:**
   * In the ViewModel or Service, check: `if (distanceMeters < 50)`. 
   * If true, trigger a local notification to say "You have arrived!" or update Firestore to show a "Status: Arrived" checkmark next to their name.
3. **Battery Saving:**
   * Reduce the `LocationRequest` interval to 30 seconds if the user's phone is still (speed is 0 for > 2 minutes).

---

## 💡 Recommended Resource Path for You
Since you know the basics, jump straight into these specific resources as you hit each phase:
1. **Architecture:** Read up on *Philip Lackner's* Clean Architecture tutorials on YouTube.
2. **Maps Compose:** Look at the official Google GitHub repo: [android-maps-compose](https://github.com/googlemaps/android-maps-compose/tree/main/app/src/main/java/com/google/maps/android/compose) and review their examples.
3. **Foreground Services & Location in Android 14+:** Review the latest Android Developers documentation on *Service Types* since Android 14 restricts services heavily.
4. **Firebase Coroutines:** Read about the `kotlinx-coroutines-play-services` library which allows `.await()` on Firebase Tasks.
