# Portfolio — Native Android App (Kotlin + Jetpack Compose)

A single-screen native Android portfolio profile app: photo, name, title,
ID, email, phone, address, and hobbies, built with Kotlin and Jetpack
Compose / Material 3.

## Project structure

```
Main/
├── build.gradle.kts               Root build script (plugin versions)
├── settings.gradle.kts            Module list
├── gradle.properties
└── app/
    ├── build.gradle.kts           App module dependencies (Compose, Coil)
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   ├── res/                Strings, base theme, backup rules
        │   └── java/com/portfolio/app/
        │       ├── MainActivity.kt         Entry point
        │       ├── model/Profile.kt        Data model
        │       ├── data/ProfileRepository.kt   Data source (swap-in ready)
        │       ├── util/TextUtils.kt        Pure helper functions
        │       └── ui/
        │           ├── theme/               Color, Type, Theme
        │           └── screen/
        │               ├── ProfileScreen.kt        Screen composition
        │               └── ProfileComponents.kt    Reusable UI pieces
        └── test/java/com/portfolio/app/util/TextUtilsTest.kt
```

## Design approach

- **One data model, one repository interface.** `Profile` is a plain,
  validated data class; `ProfileRepository` is the only thing the UI
  depends on for data, so a real network or database source can replace
  `InMemoryProfileRepository` without touching any UI code.
- **Small, composable UI pieces.** `ProfileScreen.kt` only arranges
  sections; the actual building blocks (avatar, detail row, chip, section
  card) live in `ProfileComponents.kt` and are each independently
  reusable and previewable.
- **Restrained visual language.** A single accent color (muted teal) on
  a neutral slate/paper palette, generous whitespace, rounded cards, and
  text-only chips for hobbies — deliberately avoiding icon clutter or
  saturated colors.
- **Pure logic kept out of Composables.** Initials generation and email
  validation live in `TextUtils`, a plain Kotlin object with no Android
  dependency, so it's covered by fast local unit tests (`TextUtilsTest`).

## Running it

### Option A — Android Studio (recommended for first run)
1. Open the `Main/` folder as a project in Android Studio.
2. Let Gradle sync (it will download the Compose BOM, Coil, etc.).
3. Run the `app` configuration on an emulator or device.

### Option B — VS Code
1. Install the **Kotlin** extension (`fwcd.kotlin`) and the
   **Android iOS Emulator** or **Gradle for Java** extensions if you
   want in-editor run/debug support.
2. Open `Main/` as the workspace root.
3. From a terminal in that folder:
   ```bash
   ./gradlew assembleDebug      # builds app/build/outputs/apk/debug/app-debug.apk
   ./gradlew installDebug       # installs to a connected device/emulator
   ./gradlew test               # runs TextUtilsTest
   ```
   (You'll need the Android SDK installed and `ANDROID_HOME` set; VS Code
   itself doesn't ship an emulator, so pair it with `adb`/Android Studio's
   emulator, or a physical device with USB debugging enabled.)
4. The Gradle wrapper (`gradlew` / `gradlew.bat`) isn't included in this
   export — run `gradle wrapper` once inside `Main/` (with a local Gradle
   8.x install) to generate it, or open the folder in Android Studio once
   to have it generated automatically.

## Customizing the data

Edit `InMemoryProfileRepository.getProfile()` in
`app/src/main/java/com/portfolio/app/data/ProfileRepository.kt` to change
the displayed name, ID, contact details, address, bio, or hobbies. Point
`photoUrl` at any reachable image URL — the UI automatically falls back to
lettered initials if the photo is missing or fails to load, so no image
asset is required to run the app.
