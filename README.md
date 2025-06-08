# Module AccelerometerTouchTrackerSdk

# Integration Guide: [Weather Gini SDK]

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

---

## Introduction

This guide provides step-by-step instructions for integrating [Weather Sdk] into your project.
Follow the instructions to quickly get started and connect your system with [Weather Sdk].

---

## Prerequisites
Before you begin the integration process, make sure you have the following:
- A ApiKey service from [Weather Sdk]
- Access to your [system’s] configuration settings

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
          implementation 'com.gini.weatherSdk:latest'
       }

### Step 2: Configure the Integration

Next, you’ll need to configure your integration settings.
Add the following details in your application class .
AccelerometerTouchTrackerSdkBuilder.initialize(
this,
"your api key"
)

### Step 3: Test the Integration

#### Update sdk status to launch
        AccelerometerTouchTrackerSdkBuilder.sdkStatus.value =
                            WeatherSdkStatus.OnLaunchForecast(cityName)

#### Observe SDK status and replace it with  ForecastScreenFragment
     AccelerometerTouchTrackerSdkBuilder.sdkStatus.observe(this) {
            if (it is WeatherSdkStatus.OnFinish) {
                replace(EnterCityScreenFragment(), EnterCityScreenFragment::class.java.name)
            } else if (it is WeatherSdkStatus.OnLaunchForecast) {
                replace(
                    ForecastScreenFragment.newInstance(it.cityName),
                    ForecastScreenFragment::class.java.name
                )
            }
        }

#### Observe SDK status and replace it with ForecastScreen if you are using compose
    val sdkStatus = AccelerometerTouchTrackerSdkBuilder.sdkStatus.observeAsState()
    LaunchedEffect(sdkStatus.value) {
        val current = sdkStatus.value
        if (current is WeatherSdkStatus.OnFinish) {
            navController.navigateUp()
        } else if (current is WeatherSdkStatus.OnLaunchForecast) {
            navController.navigate(NavigationItem.forecastRouteWithParams(current.cityName))
        }
    }

#### You can replace ForecastScreenFragment.newInstance(it.cityName) or ForecastScreen compose directly

## Configuration Object

You can configure the SDK using `TouchTrackerConfig` and its builder:

```kotlin
import com.behaviosec.android.accelerometerTouchTrackerSdk.config.TouchTrackerConfig
import com.behaviosec.android.accelerometerTouchTrackerSdk.logging.LogLevel

val config = TouchTrackerConfig.Builder()
    .setLoggingEnabled(true)
    .setLogLevel(LogLevel.DEBUG)
    .setEventFilter(MyCustomEventFilter())
    .build()

val accelerometerManager = AccelerometerManager(context, config)
val activityTouchManager = ActivityTouchManager(activity, config)
```

This allows you to customize logging, event filtering, and other behaviors.

## Demo

### Screenshots

- **Dashboard View**

| !["](./Docs/Screenshot_2025-02-02-13-06-07-136_com.adham.gini.weatherSDK.jpg) | !["](./Docs/Screenshot_2025-02-02-13-06-11-775_com.adham.gini.weatherSDK.jpg) | !["](./Docs/Screenshot_2025-02-02-13-06-21-436_com.adham.gini.weatherSDK.jpg) |
|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|-------------------------------------------------------------------------------|

## Authorization
You need to get Gini Weather Api key (e.g register to website )

## Api reference
https://www.weatherbit.io/
https://www.weatherbit.io/api/weather-current
https://www.weatherbit.io/api/weather-forecast-hourly

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
  -keep class com.behavio