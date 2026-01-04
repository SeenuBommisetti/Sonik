# Sonik - Modern KMP Music Player 🎵

A robust Android music player application built with **Kotlin Multiplatform (KMP)** and **Jetpack Compose**. This project demonstrates proficiency in modern Android architecture, reactive UI, and clean code principles.

## 📱 Tech Stack & Architecture
* **Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture principles.
* **UI:** Jetpack Compose (Material 3) — chosen for its declarative nature and superior state management over XML/RecyclerView.
* **Networking:** Ktor (Shared Multiplatform Module) — isolates API logic for potential iOS reusability.
* **Concurrency:** Kotlin Coroutines & StateFlow.
* **Media:** Android MediaPlayer with Lifecycle-aware state handling.
* **Image Loading:** Coil.

## 🚀 Features Implemented
* **Multi-mode Sorting:** Sort tracks by **Name (A-Z)** or **Duration**.
* **Smart Audio Player:** * Real-time progress bar and duration timer.
    * Next/Previous track functionality.
    * Buffering state indication (loading spinner in place of play button).
* **Resilient Networking:**
    * Graceful error handling with "Retry" functionality.
    * Specific handling for stream failures (e.g., network drop during playback).
* **Clean Architecture:** Data layer (API models & Repository) is separated into the `commonMain` shared module.

## 🛠 decisions & Assumptions
* **API Choice:** I chose the **Jamendo API** because it provides a reliable, open-source music catalog with rich metadata (thumbnails, duration, artist names) and requires no complex OAuth for demo purposes.
* **UI Framework:** I opted for **Jetpack Compose**. This decision demonstrates familiarity with the modern industry standard for Android UI, ensuring the code is future-proof and more readable.
* **Assumption:** The user has an active internet connection. If not, the app displays a user-friendly error screen rather than crashing.

## ⚙️ How to Run
1.  Clone this repository.
2.  Open the project in **Android Studio** (Ladybug or newer recommended).
3.  Sync Gradle (ensure KMP plugin support is active).
4.  Run the `composeApp` configuration on an Emulator or Physical Device.
    * *Note:* No API Key configuration is needed; the demo Client ID is embedded for review convenience.

## 📦 Deliverables
The compiled APK file is included in this repository as `sonik-debug.apk` for quick testing.

---

This is a Kotlin Multiplatform project targeting Android, iOS, Desktop (JVM).

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
