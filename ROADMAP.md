# 📍 NearBy — Complete Development Roadmap & Learning Guide

> **Author:** Ismam Hasan Ovi — 4th Year, Dept. of Software Engineering
> **Skill Level:** Knows Kotlin basics, Jetpack Compose basics, Firebase basics
> **Goal:** Build the entire NearBy app yourself, without AI-generated code

---

## 📊 Current Project Status

You have already scaffolded the full Clean Architecture skeleton. You have **40+ files** created with the correct package structure. Here is what is **done** vs what **needs real code**:

| Layer | Files Created | Status |
|---|---|---|
| `core/common` | `Resource.kt`, `UiEvent.kt`, `UiText.kt` | ✅ Complete |
| `core/constants` | `AppConstants.kt`, `RouteConstants.kt` | ✅ Complete |
| `core/theme` | `Color.kt`, `Theme.kt`, `Type.kt` | ⚠️ Has code but needs M3 polish |
| `core/utils` | `PermissionUtils.kt`, `ToastExtensions.kt` | ⚠️ Skeleton only |
| `di` | `AppModule.kt`, `RepositoryModule.kt`, `UseCaseModule.kt` | ✅ Complete |
| `domain/model` | `User`, `Group`, `GroupMember`, `SharedLocation` | ✅ Complete |
| `domain/repository` | All 4 interfaces | ✅ Complete |
| `domain/usecase` | All 10 use cases | ✅ Complete |
| `data/repository` | All 4 implementations | ✅ Complete (logic works) |
| `data/local` | `UserPreferences`, `LocationDao`, `AppDatabase` | ✅ Complete |
| `data/location` | `LocationManager.kt` | ✅ Complete (`callbackFlow` done) |
| `data/remote` | `FcmMessagingService.kt` | ⚠️ Skeleton only |
| `presentation/navigation` | `AppNavGraph.kt`, `Screen.kt` | ✅ Complete |
| `presentation/splash` | `SplashScreen.kt`, `SplashViewModel.kt` | ⚠️ Logic works, UI is placeholder |
| `presentation/auth` | Login & Register (Screen + ViewModel) | ⚠️ Logic works, UI is basic |
| `presentation/home` | `HomeScreen.kt`, `HomeViewModel.kt` | ❌ Placeholder text only |
| `presentation/group` | Create & Details (Screen + ViewModel) | ❌ Placeholder text only |
| `presentation/map` | `MapScreen.kt`, `MapViewModel.kt` | ❌ Placeholder text only, no Map SDK |
| `presentation/settings` | `SettingsScreen.kt`, `SettingsViewModel.kt` | ❌ Placeholder text only |
| `service` | `LocationTrackingService.kt` | ⚠️ Structure done, not wired to Firestore |

**Summary:** Your architecture, DI, navigation, domain layer, and data layer are essentially complete. What remains is building the actual **UI screens** and **wiring** the service to push location data to Firestore.

---

## 📚 Topics You Need to Learn (In Order)

Follow this sequence. Each topic directly feeds into the next coding step.

### 1. Jetpack Compose — Material 3 Components (For UI Screens)
You need this **immediately** to build every screen.

**What to study:**
- `Scaffold`, `TopAppBar`, `FloatingActionButton`
- `LazyColumn` for scrollable lists
- `Card`, `OutlinedTextField`, `OutlinedButton`, `FilledTonalButton`
- `ModalBottomSheet` for duration picker and group join
- `AlertDialog` for confirmations
- `AnimatedVisibility`, `animateContentSize` for micro-animations
- Material 3 color system: `MaterialTheme.colorScheme.surface`, `surfaceVariant`, `primary`, `onPrimary`
- Dynamic theming with `dynamicDarkColorScheme()` / `dynamicLightColorScheme()`

**Where to learn:**
- [Official M3 Compose docs](https://developer.android.com/develop/ui/compose/designsystems/material3)
- YouTube: Philipp Lackner — "Material 3 Jetpack Compose"

---

### 2. Kotlin StateFlow & Channel — For ViewModel → UI Communication
You are already using this in `LoginViewModel`. Deepen your understanding.

**What to study:**
- `MutableStateFlow` + `.asStateFlow()` for UI state
- `Channel<UiEvent>` + `.receiveAsFlow()` for one-time events (toasts, navigation)
- `collectAsState()` in Compose to observe flows
- `LaunchedEffect` to collect one-time events in Compose
- `combine()` and `map()` flow operators

**Where to learn:**
- YouTube: Philipp Lackner — "StateFlow vs SharedFlow"
- Kotlin docs: [StateFlow and SharedFlow](https://kotlinlang.org/docs/stateflow-and-sharedflow.html)

---

### 3. Kotlin callbackFlow — For Location & Firestore Streams
You already have this in `LocationManager.kt` and `LocationRepositoryImpl.kt`. Understand it deeper.

**What to study:**
- `callbackFlow { }` builder pattern
- `trySend()` to emit values from a callback
- `awaitClose { }` to clean up listeners when the flow is cancelled
- Converting any callback-based API (GPS, Firestore snapshot) into a Flow

**Where to learn:**
- [Kotlin callbackFlow docs](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/callback-flow.html)
- YouTube: Philipp Lackner — "callbackFlow"

---

### 4. Android Permissions in Jetpack Compose
Required before you can access GPS.

**What to study:**
- `ActivityResultContracts.RequestMultiplePermissions()`
- `rememberLauncherForActivityResult()` in Compose
- Checking `shouldShowRequestPermissionRationale()` for "denied permanently" case
- Opening App Settings using `Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)`
- The 3 location permission levels: `FINE`, `COARSE`, `BACKGROUND`
- Why background location requires a **separate** permission request (Android 11+)

**Where to learn:**
- [Android Developers: Request runtime permissions](https://developer.android.com/training/permissions/requesting)

---

### 5. Android Foreground Services (Location Type)
Required to keep GPS alive when app is minimized.

**What to study:**
- `Service` lifecycle: `onCreate()`, `onStartCommand()`, `onDestroy()`
- `startForeground(id, notification)` — makes the service visible and unkillable
- Notification Channels (required Android 8+)
- Manifest declarations: `<service android:foregroundServiceType="location">`
- `FOREGROUND_SERVICE` and `FOREGROUND_SERVICE_LOCATION` permissions
- Starting/stopping the service via `Intent` actions from the UI

**Where to learn:**
- [Android Developers: Foreground services](https://developer.android.com/develop/background-work/services/foreground-services)
- YouTube: Philipp Lackner — "Foreground Service Location Tracking"

---

### 6. Google Maps SDK for Jetpack Compose
Required for the map screen.

**What to study:**
- `maps-compose` library setup (add dependency)
- `GoogleMap` composable, `CameraPositionState`, `rememberCameraPositionState`
- `Marker` composable for plotting users
- `MapProperties` and `MapUiSettings`
- Custom marker icons using `BitmapDescriptor`
- Animating camera with `cameraPositionState.animate(CameraUpdateFactory.newLatLng(...))`

**Where to learn:**
- [android-maps-compose GitHub](https://github.com/googlemaps/android-maps-compose)
- [Google Maps Platform: Android Quickstart](https://developers.google.com/maps/documentation/android-sdk/start)

---

### 7. Firestore Security Rules
Required before going to production.

**What to study:**
- `request.auth` — checking if user is authenticated
- `resource.data` — reading existing document fields
- `request.resource.data` — reading incoming write data
- Writing rules that allow only group members to read/write to `groups/{groupId}/locations`
- Testing rules in the Firebase Console Rules Playground

**Where to learn:**
- [Firebase: Firestore Security Rules](https://firebase.google.com/docs/firestore/security/get-started)

---

### 8. Firebase Cloud Messaging (FCM)
Required for push notifications (arrival alerts).

**What to study:**
- `FirebaseMessagingService` — receiving messages in background
- `onNewToken()` — saving the FCM token to Firestore
- Sending notifications from Firebase Console for testing
- (Optional) Cloud Functions to trigger notifications server-side when a user arrives

**Where to learn:**
- [Firebase: Cloud Messaging on Android](https://firebase.google.com/docs/cloud-messaging/android/client)

---

## 🛠 The Exact Coding Sequence (Step-by-Step)

This is the order in which you should open files and write code. Each step tells you **which file**, **what to do**, and **which topic** it exercises.

---

### Step 1 ✅ — Project Architecture Setup
**Status:** DONE. You have all packages, DI modules, navigation, models, repositories, and use cases.

---

### Step 2 — Build the Splash Screen UI
**Files:** `SplashScreen.kt`
**Topic:** Compose basics, `LaunchedEffect`, `AnimatedVisibility`

**What to do:**
1. Replace the plain `Text("NearBy")` with an actual splash design
2. Add an app logo/icon in the center (use a `Box` with `Alignment.Center`)
3. Add the app name below the logo with large typography using `MaterialTheme.typography.headlineLarge`
4. Add a subtle fade-in animation using `AnimatedVisibility` with `fadeIn()`
5. Keep the existing `LaunchedEffect` logic that checks auth and navigates — it already works

**Why first?** This is the entry point of your app. If this compiles and navigates correctly, your entire Hilt + Navigation + Auth check pipeline is verified.

---

### Step 3 — Build the Login Screen UI
**Files:** `LoginScreen.kt`
**Topic:** M3 `OutlinedTextField`, `Button`, form validation, `Scaffold`

**What to do:**
1. Wrap everything in a `Scaffold`
2. Replace `TextField` with `OutlinedTextField` (Material 3 style)
3. Add a password visibility toggle icon using `IconButton` inside `trailingIcon`
4. Add keyboard options: `KeyboardOptions(keyboardType = KeyboardType.Email)` for email field
5. Add an "or" divider and a "Don't have an account? Register" clickable text at the bottom
6. Handle `uiEvent` collection:
   ```
   LaunchedEffect(Unit) {
       viewModel.uiEvent.collect { event ->
           when (event) {
               is UiEvent.Navigate -> onLoginSuccess()
               is UiEvent.ShowToast -> { /* show toast */ }
               // etc
           }
       }
   }
   ```
7. Your `LoginViewModel.kt` logic is already working — no changes needed there

---

### Step 4 — Build the Register Screen UI
**Files:** `RegisterScreen.kt`, `RegisterViewModel.kt`
**Topic:** Same as Login, plus calling `RegisterUseCase`

**What to do:**
1. Mirror the Login screen layout but add a "Name" field
2. Add password confirmation field
3. In `RegisterViewModel`, validate that passwords match before calling `RegisterUseCase`
4. On success, navigate to Home (same pattern as `LoginViewModel`)

---

### Step 5 — Build the Home Screen (Group List)
**Files:** `HomeScreen.kt`, `HomeViewModel.kt`
**Topic:** `Scaffold`, `TopAppBar`, `LazyColumn`, `FloatingActionButton`, `Card`

**What to do:**
1. Add a `Scaffold` with:
   - `TopAppBar` title: "NearBy" or "My Groups"
   - Settings icon button in the top-right → calls `onNavigateToSettings()`
   - `FloatingActionButton` at bottom-right with a **+** icon → calls `onNavigateToCreateGroup()`
2. In `HomeViewModel`:
   - On init, call `GetUserGroupsUseCase()` and store result in `StateFlow<List<Group>>`
   - Expose a `UiState` with `groups: List<Group>`, `isLoading`, `error`
3. In `HomeScreen`:
   - If loading → show `CircularProgressIndicator`
   - If empty → show an empty state illustration with "Create your first group"
   - If groups exist → `LazyColumn` with a `Card` for each group showing:
     - Group name
     - Member count
     - Invite code (small, secondary color)
   - Each `Card` is clickable → `onNavigateToMap(group.id)`

---

### Step 6 — Build the Create Group Screen
**Files:** `CreateGroupScreen.kt`, `CreateGroupViewModel.kt`
**Topic:** Form UI, calling `CreateGroupUseCase`, `FieldValue.arrayUnion`

**What to do:**
1. Simple form with `OutlinedTextField` for group name and optional description
2. A "Create" button
3. In `CreateGroupViewModel`:
   - Call `CreateGroupUseCase(name, description)`
   - On success, send `UiEvent.Navigate("back")` and call `onGroupCreated()`
4. **Important fix to make in `GroupRepositoryImpl.createGroup()`:**
   - The `createdBy` field is currently hardcoded as `""`. You need to inject `FirebaseAuth` into `GroupRepositoryImpl` and set `createdBy = firebaseAuth.currentUser?.uid ?: ""`
   - Also add the creator as a member in the `members` subcollection after creating the group

---

### Step 7 — Add "Join Group" Flow
**Files:** `HomeScreen.kt` (add a dialog), `HomeViewModel.kt`
**Topic:** `AlertDialog`, Firestore `whereEqualTo` query

**What to do:**
1. Add a second button or a menu option in HomeScreen: "Join Group"
2. Show an `AlertDialog` with a single `OutlinedTextField` for the invite code
3. In `HomeViewModel`:
   - Call `JoinGroupUseCase(inviteCode)`
   - Handle the result: show error toast or refresh the group list
4. **Important fix to make in `GroupRepositoryImpl.joinGroupWithCode()`:**
   - After finding the group, actually add the current user as a member using `addMember(groupId, currentUserId)`
   - Currently it only returns the group but doesn't actually add the user

---

### Step 8 — Build Group Details Screen
**Files:** `GroupDetailsScreen.kt`, `GroupDetailsViewModel.kt`
**Topic:** `LazyColumn`, real-time Firestore listener, member list

**What to do:**
1. Display: Group name, description, invite code (with a "Copy" button)
2. Member list (`LazyColumn`) showing each member's name, role badge (Admin/Member), and sharing status
3. In `GroupDetailsViewModel`:
   - Call `observeGroupMembers(groupId)` from `GroupRepository` (this is a Flow — collect it)
   - Store members in `StateFlow<List<GroupMember>>`
4. Add buttons:
   - "Share Location" → triggers location sharing flow (Step 11)
   - "Open Map" → `onNavigateToMap(groupId)`
   - "Leave Group" (if member) / "Delete Group" (if admin)

---

### Step 9 — Location Permissions Flow
**Files:** `PermissionUtils.kt`, `MapScreen.kt` or a new `PermissionScreen.kt`
**Topic:** Runtime permissions, `rememberLauncherForActivityResult`

**What to do:**
1. In `PermissionUtils.kt`, create helper functions:
   - `hasLocationPermission(context): Boolean`
   - `hasFineLocationPermission(context): Boolean`
   - `hasBackgroundLocationPermission(context): Boolean`
2. In the screen that needs location (either `MapScreen` or `GroupDetailsScreen`):
   ```kotlin
   val permissionLauncher = rememberLauncherForActivityResult(
       ActivityResultContracts.RequestMultiplePermissions()
   ) { permissions ->
       val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
       val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
       // update viewmodel state
   }
   ```
3. If permissions not granted → show a rationale UI with "Location is required to share your position"
4. If permanently denied → show a button that opens app settings
5. **Manifest updates:**
   ```xml
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
   <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
   <uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />
   <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
   ```

---

### Step 10 — Integrate Google Maps SDK
**Files:** `MapScreen.kt`, `MapViewModel.kt`
**Topic:** `maps-compose` library, Google Cloud Console API key

**What to do:**
1. **Setup:**
   - Go to Google Cloud Console → Enable "Maps SDK for Android"
   - Get an API key → add to `AndroidManifest.xml`:
     ```xml
     <meta-data
         android:name="com.google.android.geo.API_KEY"
         android:value="${MAPS_API_KEY}" />
     ```
   - Add API key to `local.properties`: `MAPS_API_KEY=your_key_here`
   - Add to `build.gradle.kts` `defaultConfig`: `manifestPlaceholders["MAPS_API_KEY"] = properties["MAPS_API_KEY"] ?: ""`
   - Add `maps-compose` dependency: `implementation("com.google.maps.android:maps-compose:6.4.1")`
2. **Build the map UI:**
   ```kotlin
   val cameraPositionState = rememberCameraPositionState {
       position = CameraPosition.fromLatLngZoom(userLatLng, 15f)
   }
   GoogleMap(
       modifier = Modifier.fillMaxSize(),
       cameraPositionState = cameraPositionState,
       properties = MapProperties(isMyLocationEnabled = hasPermission)
   ) {
       // Markers will go here in Step 12
   }
   ```
3. In `MapViewModel`:
   - Get the user's current location on init using `LocationManager.getCurrentLocation()`
   - Store it in state so the camera can center on it

---

### Step 11 — Wire Foreground Service to Firestore
**Files:** `LocationTrackingService.kt`, `LocationRepositoryImpl.kt`
**Topic:** Foreground Service, `callbackFlow`, Firestore writes

**What to do:**
1. **In `LocationTrackingService.kt`:**

   Your service structure is already correct. The missing piece is pushing to Firestore:
   - Inject `LocationRepositoryImpl` (or use `LocationRepository` interface) via Hilt `@AndroidEntryPoint`
   - Get the `groupId` from the intent extras: `intent?.getStringExtra("GROUP_ID")`
   - In `startLocationTracking()`, collect the flow and call `locationRepository.updateLocation(...)`:
     ```kotlin
     locationManager.getLocationUpdates()
         .collect { location ->
             locationRepository.updateLocation(
                 groupId = groupId,
                 latitude = location.latitude,
                 longitude = location.longitude,
                 accuracy = location.accuracy,
                 speed = location.speed,
                 bearing = location.bearing
             )
         }
     ```

2. **Fix in `LocationRepositoryImpl.updateLocation()`:**
   - Replace the hardcoded `"current_user"` document ID with the actual `firebaseAuth.currentUser?.uid`
   - Inject `FirebaseAuth` into `LocationRepositoryImpl`

3. **Duration auto-stop:**
   - Get duration from intent extras: `intent?.getLongExtra("DURATION_MS", 0L)`
   - Launch a parallel coroutine:
     ```kotlin
     if (durationMs > 0) {
         serviceScope.launch {
             delay(durationMs)
             stopSelf()
         }
     }
     ```

4. **Start/Stop from UI:**
   In `GroupDetailsScreen`, when user taps "Share Location":
   ```kotlin
   val intent = Intent(context, LocationTrackingService::class.java).apply {
       action = LocationTrackingService.ACTION_START
       putExtra("GROUP_ID", groupId)
       putExtra("DURATION_MS", selectedDuration)
   }
   context.startForegroundService(intent)
   ```

5. **Manifest registration:**
   ```xml
   <service
       android:name=".service.LocationTrackingService"
       android:foregroundServiceType="location"
       android:exported="false" />
   ```

---

### Step 12 — Real-Time Multi-User Map Markers
**Files:** `MapScreen.kt`, `MapViewModel.kt`
**Topic:** Firestore `snapshotListener` via Flow, dynamic Compose markers

**What to do:**
1. In `MapViewModel`:
   - You already have `observeLocations(groupId)` calling `ObserveGroupLocationsUseCase`
   - Store the emitted `List<SharedLocation>` in a `StateFlow`
2. In `MapScreen`:
   - Inside the `GoogleMap { }` content lambda, loop through locations:
     ```kotlin
     uiState.memberLocations.forEach { location ->
         Marker(
             state = MarkerState(position = LatLng(location.latitude, location.longitude)),
             title = location.userId, // later replace with display name
             snippet = "Speed: ${location.speed} m/s"
         )
     }
     ```
3. **Bonus — Add user names:** Create a map of `userId → displayName` by fetching user profiles for each member in the group. Display the name on the marker title.

---

### Step 13 — Duration Picker Bottom Sheet
**Files:** `GroupDetailsScreen.kt` or `MapScreen.kt`
**Topic:** `ModalBottomSheet`, Radio buttons

**What to do:**
1. Create a `ModalBottomSheet` with options:
   - 15 minutes
   - 1 hour
   - Custom (show a `Slider` or number picker)
   - Continuous (until manually stopped)
2. On selection, start the foreground service with the chosen duration (Step 11)
3. Show a "Stop Sharing" button that sends `ACTION_STOP` intent to the service

---

### Step 14 — ETA & Distance Calculation
**Files:** `MapViewModel.kt`, `MapScreen.kt`
**Topic:** `Location.distanceTo()`, basic math

**What to do:**
1. **Set destination:**
   - Add a long-press listener on the map: `onMapLongClick = { latLng -> viewModel.setDestination(latLng) }`
   - Save destination `GeoPoint` to Firestore on the group document
   - Show a special marker (different color) for the destination
2. **Calculate distance in ViewModel:**
   ```kotlin
   val userLoc = Location("").apply { latitude = member.lat; longitude = member.lng }
   val destLoc = Location("").apply { latitude = dest.lat; longitude = dest.lng }
   val distanceMeters = userLoc.distanceTo(destLoc)
   ```
3. **Calculate ETA:**
   ```kotlin
   val speedMps = member.speed
   val etaSeconds = if (speedMps > 0.5f) (distanceMeters / speedMps).toLong() else null
   val etaFormatted = etaSeconds?.let { "${it / 60} min" } ?: "Stationary"
   ```
4. **Display in a BottomSheet:**
   - `ModalBottomSheet` showing a `LazyColumn` of members with:
     - Profile picture / initial avatar
     - Name
     - Distance: "1.2 km away"
     - ETA: "~8 min" or "Stationary"
     - Status: "Sharing" / "Not sharing"

---

### Step 15 — Settings Screen
**Files:** `SettingsScreen.kt`, `SettingsViewModel.kt`
**Topic:** `Switch`, preferences, sign-out flow

**What to do:**
1. Build a proper settings list with:
   - Profile section: Name, email (read from DataStore/Firestore)
   - Toggle: "Battery saver mode" → saves to `UserPreferences.setBatterySaverMode()`
   - Toggle: "Location sharing default" → saves to `UserPreferences.setLocationSharingEnabled()`
   - Button: "Sign Out" → calls `SignOutUseCase()`, clears `UserPreferences.clearAll()`, navigates to Login
2. Use M3 `ListItem` composable for clean, native-looking rows

---

### Step 16 — Push Notifications (FCM)
**Files:** `FcmMessagingService.kt`, `UserRepositoryImpl.kt`
**Topic:** FCM token, notifications

**What to do:**
1. In `FcmMessagingService.kt`:
   - Override `onNewToken(token)` → save token to Firestore under `users/{userId}/fcmToken`
   - Override `onMessageReceived(remoteMessage)` → show a local notification
2. **Arrival detection:**
   - In `LocationTrackingService` or `MapViewModel`, when distance < 50 meters:
     - Update Firestore: set a `status: "arrived"` field on the member's location document
     - Trigger a local notification: "You have arrived at the meetup!"
3. (Optional) Set up a Firebase Cloud Function that watches for `status: "arrived"` and sends FCM to all group members

---

### Step 17 — Battery Optimization
**Files:** `LocationManager.kt`, `LocationTrackingService.kt`
**Topic:** Adaptive intervals, `Priority` enum

**What to do:**
1. Check `UserPreferences.isBatterySaverMode`
2. If battery saver is ON:
   - Use `Priority.PRIORITY_BALANCED_POWER_ACCURACY` instead of `HIGH_ACCURACY`
   - Set interval to 15000ms instead of 5000ms
3. Detect if user is stationary (speed == 0 for 2+ minutes):
   - Temporarily increase interval to 30000ms
   - When movement resumes, switch back to normal interval

---

### Step 18 — UI Polish & Final Touches
**Files:** All screens
**Topic:** Animations, error states, empty states

**What to do:**
1. Add `AnimatedVisibility` for loading → content transitions on every screen
2. Add pull-to-refresh on HomeScreen using `pullToRefresh` modifier
3. Add proper error states: a retry button when Firestore calls fail
4. Add empty states: illustrations when group list is empty
5. Add `Snackbar` host in `Scaffold` for error messages from `UiEvent.ShowSnackbar`
6. Test the complete flow: Register → Create Group → Share Code → Join from another device → See both markers on map → ETA updates live

---

## 🧪 Testing Your App Without Running Outside

Android Studio Emulator supports **simulated GPS routes**:
1. Open Emulator → `...` (Extended Controls) → **Location**
2. Click **Routes** tab → Draw a route on the map → Click **Play Route**
3. Your app will receive moving GPS updates as if you are walking/driving
4. Use **two emulators** side by side to test multi-user location sharing

---

## 📂 Your Firestore Database Schema

```
📁 users (collection)
  └── 📄 {userId} (document)
        ├── id: string
        ├── displayName: string
        ├── email: string
        ├── photoUrl: string?
        ├── isOnline: boolean
        ├── lastSeen: timestamp
        ├── createdAt: timestamp
        └── fcmToken: string

📁 groups (collection)
  └── 📄 {groupId} (document)
        ├── id: string
        ├── name: string
        ├── description: string
        ├── createdBy: string (userId)
        ├── createdAt: timestamp
        ├── memberCount: int
        ├── inviteCode: string
        ├── destination: { lat: double, lng: double }?
        │
        ├── 📁 members (subcollection)
        │     └── 📄 {userId}
        │           ├── userId: string
        │           ├── role: "ADMIN" | "MEMBER"
        │           ├── joinedAt: timestamp
        │           ├── isSharingLocation: boolean
        │           └── sharingExpiresAt: timestamp
        │
        └── 📁 locations (subcollection)
              └── 📄 {userId}
                    ├── latitude: double
                    ├── longitude: double
                    ├── accuracy: float
                    ├── speed: float
                    ├── bearing: float
                    ├── timestamp: timestamp
                    └── isSharingActive: boolean
```

---

## ⚠️ Known Issues in Your Current Code to Fix

These are bugs I spotted while reading your source code. Fix them as you reach the relevant step.

1. **`GroupRepositoryImpl.createGroup()` — Line 32:** `createdBy` is hardcoded as `""`. Inject `FirebaseAuth` and use `firebaseAuth.currentUser?.uid`.

2. **`GroupRepositoryImpl.createGroup()` — Missing member addition:** After creating the group document, you also need to call `addMember(groupId, currentUserId)` to create the creator as an ADMIN in the `members` subcollection.

3. **`GroupRepositoryImpl.joinGroupWithCode()` — Missing member addition:** It finds the group but never actually adds the user to the members subcollection. Call `addMember()` after finding the group.

4. **`GroupRepositoryImpl.getUserGroups()` — Wrong query:** Currently fetches ALL groups in the database. Should query only groups where the current user exists in the `members` subcollection, or maintain a `groupIds` array on the user document and query by those IDs.

5. **`LocationRepositoryImpl.updateLocation()` — Line 68:** Uses hardcoded `"current_user"` as document ID. Replace with `firebaseAuth.currentUser?.uid`.

6. **`LocationManager.getLocationUpdates()` — Missing `@SuppressLint`:** The `requestLocationUpdates()` call requires the `"MissingPermission"` annotation since the permission is checked at the UI layer.

7. **`LocationTrackingService` — Not injecting repository:** The service collects locations but only logs them. It needs to inject `LocationRepository` and actually push updates to Firestore.

---

## 💡 Recommended YouTube Channels & Resources

| Topic | Resource |
|---|---|
| Clean Architecture + Hilt | Philipp Lackner — "MVVM Clean Architecture" series |
| Compose M3 Components | Official Android Developers — Compose samples on GitHub |
| Maps in Compose | [android-maps-compose samples](https://github.com/googlemaps/android-maps-compose/tree/main/app) |
| Foreground Service | Philipp Lackner — "Location Tracking Foreground Service" |
| Firestore + Kotlin | Firebase Official — [Kotlin Extensions guide](https://firebase.google.com/docs/android/kotlin-extensions) |
| FCM Notifications | Firebase Official — [Cloud Messaging guide](https://firebase.google.com/docs/cloud-messaging/android/client) |
| Testing with Emulator GPS | Android Docs — [Set up a virtual device](https://developer.android.com/studio/run/emulator-send-location-data) |
