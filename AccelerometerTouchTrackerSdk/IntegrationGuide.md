# Module AccelerometerTouchTrackerSdk

# Integration Guide: [AccelerometerTouchTrackerSdk]

## Table of Contents
1. [Introduction]
2. [Prerequisites]
3. [Setup Instructions]
   1. [Step 1: Install Dependencies]
   2. [Step 2: Configure the Integration]
   3. [Step 3: Test the Integration]
4. [Demo]
5. [Authentication]
6. [API Reference]
7. [Common Errors & Troubleshooting]
8. [FAQs]
9. [Support]
10. [Testing Recommendations]
11. [Securing the Application with ProGuard/R8 Rules]
12. [Documentation Generation]
13. [Areas for Enhancement]


---

## Introduction

This guide provides step-by-step instructions for integrating [AccelerometerTouchTrackerSdk] into your project.
Follow the instructions to quickly get started and connect your system with [AccelerometerTouchTrackerSdk].

---

## Prerequisites
Before you begin the integration process, make sure you have the following:
- Access to your [system’s] configuration settings
- In future will add A ApiKey service from [AccelerometerTouchTrackerSdk]


## Setup Instructions

### Step 1: Install Dependencies
Start by installing the required dependencies to your project.
Use one of the following commands based on your package manager.
use Gradle:

       repositories {
           google()
           mavenCentral()
       }
       
       dependencies {
          implementation 'com.relx.AccelerometerTouchTrackerSdk:latest'
       }

### Step 2: Configure the Integration

Next, you’ll need to configure your integration settings.
Add the following details in your application class .
AccelerometerTouchTrackerCore.initialize()
**We will add "AccelerometerTouchTrackerSdk api key" in the forthcoming releases **

### Step 3: Test the Integration

#### Use Managers with related callback interfaces
        AccelerometerManager with AccelerometerListener and  errorListener
        ActivityTouchManager with ActivityTouchListener and  errorListener
        AppTouchManager with AppTouchListener and  errorListener

#### Use TouchSensorViewModel to observe the data
        val touchSensorViewModel = ViewModelProvider(this).get(TouchSensorViewModel::class.java)
        touchSensorViewModel.lastAccelerometerEvent.observe(this) { events ->
            // Handle Accelerometer
        }
        touchSensorViewModel.lastAccuracyEvent.observe(this) { events ->
            // Handle Accuracy
        }
        touchSensorViewModel.accelerometerError.observe(this) { error ->
            // Handle errors
        }
        touchSensorViewModel.lastMotionEvent.observe(this) { events ->
            // Handle Touch Events
        }
        touchSensorViewModel.motionError.observe(this) { error ->
             // Handle errors
        }

## Configuration Object

You can configure the SDK using `TouchTrackerConfig`:

```kotlin
import com.behaviosec.android.accelerometerTouchTrackerSdk.config.TouchTrackerConfig
import com.behaviosec.android.accelerometerTouchTrackerSdk.logging.LogLevel

val config = TouchTrackerConfig()
    .setLoggingEnabled(true)
    .setLogLevel(LogLevel.DEBUG)
 

val accelerometerManager = AccelerometerManager(context, config)
val activityTouchManager = ActivityTouchManager(activity, config)
```

This allows you to customize logging, and other behaviors.

## Demo

### Screenshots

- **Dashboard View**

| !["](./Screenshot_20250610_023950.png) | !["](./Docs/Screenshot_20250610_024108.png) | !["](./Docs/Screenshot_20250610_024149.png) |
|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|

## Authorization
So far no authentication is required for the SDK. In future releases, we will add an API key service to enhance security.

## Common Errors & Troubleshooting
https://github.com/adhamkhwaldeh/AccelerometerTouchTrackerSdk/issues

## FAQs
https://github.com/adhamkhwaldeh/AccelerometerTouchTrackerSdk/issues

## Support
https://github.com/adhamkhwaldeh/AccelerometerTouchTrackerSdk

## Testing Recommendations

To further improve your SDK's reliability, add more **Unit Tests** and **Instrumentation Tests**:

### Unit Tests
- Cover all public methods in managers, listeners, models, and utility classes.
- Use mocking frameworks (e.g., Mockito, MockK) for Android dependencies.
- Test event filtering, error handling, and configuration logic.
- Validate logger and metrics integration.
- Ensure ViewModel logic and LiveData updates are tested.

**Example Unit Test Files:**
- `src/test/java/com/behaviosec/android/accelerometerTouchTrackerSdk/managers/AccelerometerManagerTest.kt`
- `src/test/java/com/behaviosec/android/accelerometerTouchTrackerSdk/managers/ActivityTouchManagerTest.kt`
- `src/test/java/com/behaviosec/android/accelerometerTouchTrackerSdk/viewmodel/TouchSensorViewModelTest.kt`

### Instrumentation Tests
- Test integration with Android components (Activity, Service, etc.).
- Simulate real touch and sensor events on device/emulator.
- Verify lifecycle-aware behavior (start/stop tracking).
- Validate LiveData and UI updates in real scenarios.

**Example Instrumentation Test Files:**
- `src/androidTest/java/com/behaviosec/android/accelerometerTouchTrackerSdk/AccelerometerIntegrationTest.kt`
- `src/androidTest/java/com/behaviosec/android/accelerometerTouchTrackerSdk/TouchEventIntegrationTest.kt`

> **Tip:**  
> Place unit tests in `src/test/java` and instrumentation tests in `src/androidTest/java` under the appropriate package.

Add tests for any new features or bug fixes to maintain high coverage and confidence in your SDK.

## Securing the Application with ProGuard/R8 Rules

To secure your application and SDK with ProGuard or R8:

- **Enable minification and obfuscation** in your `build.gradle`:
  ```groovy
  buildTypes {
      release {
          minifyEnabled true
          proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
      }
  }
  ```

- **Add these rules to your `proguard-rules.pro` file:**
  ```proguard
  # Keep SDK API surface
  -keep class com.behaviosec.android.accelerometerTouchTrackerSdk.** { *; }
  ```
## Documentation Generation

The SDK uses **KDoc** for inline documentation of all public classes, methods, and properties.  
Documentation is automatically generated using [Dokka](https://github.com/Kotlin/dokka).

### How to Generate Documentation

1. **Write KDoc Comments:**  
   All public APIs are documented using KDoc.  
   Example:
   ```kotlin
   /**
    * Starts tracking accelerometer events.
    * @param config The configuration object.
    */
   fun startTracking(config: TouchTrackerConfig){}
   ```

2. **Generate HTML Documentation with Dokka:**  
   Run the following Gradle command from the project root:
   ```
   ./gradlew dokkaHtml
   ```
   The generated documentation will be available in the `build/dokka/html` directory.

3. **Custom Guides:**  
   You can add Markdown files (e.g., `IntegrationGuide.md`) to be included in the generated documentation.

> Keeping your KDoc up to date ensures that the generated documentation is always accurate and helpful for SDK consumers.


## Areas for Enhancement

The following areas are recommended for further enhancement to improve the SDK's robustness, flexibility, and usability:

- **Analytics Integration:** Provide hooks for integrating with analytics or crash reporting tools.
- **Add SonarQube &Unit & Instrumentation Tests** to cover all required test cases (many cases need to be covered I've added samples only),
  We need to increase test coverage, especially for edge cases and error handling.
- **Add Dependency Injection** Support DI frameworks (like Hilt or Dagger) for easier integration and testing.
- **Introduce UseCases** I just Implemented the code in simple way but we have to introduce use cases between Manager or Repository and ViewModel for more complex cases
- **Add Sample Fore ViewModel** we I've implemented Managers as Sample and add the viewModel in the documentation only

> **Contributions and suggestions are welcome!**  
> Please open an issue or pull request on [GitHub](https://github.com/adhamkhwaldeh/AccelerometerTouchTrackerSdk) for any enhancements.

