# Module: UserBehaviorSDK
    }
}
```

Coroutine / Flow usage example (in Compose or Activity):

This guide shows how to integrate and use the UserBehavior SDK that is included in this repository. It reflects the public APIs and sample code used in the project (for example `UserBehaviorCoreSDK`, manager APIs like `getAccelerometerManager(...)`, `fetchOrCreateActivityTouchManager(...)`, the Compose provider `ProvideUserBehaviorSDK`, and the sample ViewModels `LiveDataViewModel` and `CoroutineViewModel`).

- Project structure pointers:
class ComposeSampleActivity : ComponentActivity() {
    private val sdk: UserBehaviorCoreSDK by lazy { get() }
  - Sample app: `app/` (contains sample activities and ViewModels)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideUserBehaviorSDK(sdk) {
                ComposeSampleScreen()
            }
        }
    }
}
3. Initialize the SDK (Application + Koin)
@Composable
fun ComposeSampleScreen() {
    val sdk = LocalUserBehaviorCoreSDK.current
    // build UI and interact with sdk
}
6. Compose integration (ProvideUserBehaviorSDK)
7. Sample usage in Activities (LiveData & Compose)
The sample `ComposeSampleScreen` mirrors the XML sample layout (`activity_xml_sample.xml`) and demonstrates how to start/stop managers and show sensor data.
---
## 7. Sample usage patterns
## 1. Prerequisites
- Start a manager and add listeners:
- Android project with Kotlin support.
```kotlin
val accel = sdk.getAccelerometerManager(AccelerometerConfig())
accel.addListener(object : AccelerometerListener {
    override fun onSensorChanged(m: AccelerometerEventModel) { /* handle */ }
    override fun onAccuracyChanged(m: AccuracyChangedModel) { /* handle */ }
})
accel.start()
```
- AndroidX / Jetpack components for ViewModel / LiveData and Compose if you use Compose.
- Activity-scoped touch manager:

```kotlin
val touchMgr = sdk.fetchOrCreateActivityTouchManager(activity, TouchConfig())
touchMgr.addListener(object : TouchListener { override fun dispatchTouchEvent(e) = true })
touchMgr.start()
```
In a consumer project (example coordinates):
- Stop and cleanup when not needed:
```gradle
```kotlin
accel.stop()
touchMgr.stop()
```
    // Compose provider module if published separately
## 8. ProGuard / R8 rules
In this repository you can include the `UserBehaviorSDK` and `UserBehaviorSDK-Compose` modules in settings.gradle to use them locally.
To prevent obfuscation issues for SDK consumers, add rules similar to the following:
Example `Application` setup (Koin):
```proguard
# Keep SDK API surface
-keep class com.github.adhamkhwaldeh.userBehaviorSDK.** { *; }
```
```
Also ensure your app keeps ViewModel and other AndroidX classes as required by your tooling.
This allows you to customize logging, and other behaviors.
## 9. Testing & recommendations

- Add unit tests for managers and ViewModels. Use `MockK` or `Mockito` to stub Android dependencies.
- Add instrumentation tests to validate lifecycle-aware behavior with real sensors (or mocked sensors).
- Consider adding CI checks (detekt, ktlint, unit tests) as in the repository.

---
- Use mocking frameworks (e.g., Mockito, MockK) for Android dependencies.
If you'd like, I can also:
- Add usage snippets for Timber / Logger integration used in the samples.
- Create a short README for the sample app describing how to run it.
- Add example Koin module code into `app/` for easier copy-paste.
- Validate logger and metrics integration.
Tell me which of the above you want next and I'll implement it.
    }
}

val appModule = module {
    single { UserBehaviorCoreSDK.getInstance(androidContext()) }
    // If you have configuration class, provide it here as well
}
```

Notes:
- `UserBehaviorCoreSDK.getInstance(context)` is a sample factory used in the sample app. Replace with the actual factory from the SDK.
- The sample app registers `SampleApp` in `AndroidManifest.xml`.

## 4. Managers and configuration objects

The SDK exposes manager factories and configuration classes. The samples use these APIs:

- Configuration objects
  - `AccelerometerConfig`
  - `TouchConfig`

- Manager accessors (examples)
  - `userBehaviorCoreSDK.getAccelerometerManager(AccelerometerConfig()) : IAccelerometerManager`
  - `userBehaviorCoreSDK.fetchOrCreateActivityTouchManager(activity, TouchConfig()) : ITouchManager`
  - `userBehaviorCoreSDK.createTouchManager(TouchConfig()) : ITouchManager` (context/global manager)

- Manager common methods
  - `.start()`, `.stop()`, `.setEnabled(...)`, `.setDebugMode(...)`, `.setLoggingEnabled(...)`
  - `.addListener(listener)` — add an event listener (callbacks) for sensor or touch events
  - `.addErrorListener(listener)` — add manager error listener

- Example callback interfaces (implement these to receive events)
  - `AccelerometerListener` — `onSensorChanged(AccelerometerEventModel)`, `onAccuracyChanged(AccuracyChangedModel)`
  - `TouchListener` — `dispatchTouchEvent(MotionEventModel) : Boolean`
  - Error listeners that receive `BaseSDKException`

The sample project provides convenience extension functions for LiveData and Flow in `userBehaviorSDKKtx` (used by the sample ViewModels):
- `sensorChangedLiveData()` / `sensorChangedFlow` etc. (see `LiveDataViewModel` / `CoroutineViewModel`)

## 5. ViewModels (LiveData and Flow examples)

This repo includes two sample ViewModels that show two common integration styles:

- `LiveDataViewModel` — exposes LiveData properties which are backed by the SDK LiveData extensions. Use this when your UI prefers LiveData.
- `CoroutineViewModel` — exposes StateFlow properties and collects SDK Flows. Use this when you want to use Kotlin Flow / Compose.

LiveData usage example (in an Activity):

```kotlin
class LiveDataSampleActivity : AppCompatActivity() {
    private val viewModel by lazy { LiveDataViewModel(get()) } // Koin get()

   You can add Markdown files (e.g., `IntegrationGuide.md`) to be included in the generated documentation.

> Keeping your KDoc up to date ensures that the generated documentation is always accurate and helpful for SDK consumers.

## Publishing Dokka Documentation to GitHub

To upload the generated Dokka documentation to GitHub (for example, to a `gh-pages` branch):

1. **Generate the documentation:**
   ```bash
   ./gradlew :UserBehaviorSDK:dokkaGeneratePublicationHtml
   ```
   The output will be in `UserBehaviorSDK/build/dokkaDir`.

2. **Switch to the `gh-pages` branch (or create it if it doesn't exist):**
   ```bash
   git checkout --orphan gh-pages
   ```

3. **Copy the generated documentation:**
   ```bash
   cp -r UserBehaviorSDK/build/dokkaDir/* .
   ```
   > **Note:**  
   > This command copies the generated documentation files into your working directory for the `gh-pages` branch.  
   > It does **not** add them to your project source code.  
   > You must commit and push these files on the `gh-pages` branch to publish them via GitHub Pages.

4. **Commit and push the documentation:**
   ```bash
   git add .
   git commit -m "Publish Dokka documentation"
   git push origin gh-pages --force
   ```

5. **(Optional) Automate with GitHub Actions:**  
   You can automate this process by adding a workflow that runs Dokka and pushes the output to `gh-pages` on every release or push to `main`.

> This allows you to host your documentation via GitHub Pages at `https://<username>.github.io/<repo>/`.

## Code Conventions

1. **Implement libraries ktlint and detekt**
2. **Add detekt and ktlint check within the automation in github actions**

## Areas for Enhancement

The following areas are recommended for further enhancement to improve the SDK's robustness, flexibility, and usability:

- **Analytics Integration:** Provide hooks for integrating with analytics or crash reporting tools.
- **Add SonarQube &Unit & Instrumentation Tests** to cover all required test cases (many cases need to be covered I've added samples only),
  We need to increase test coverage, especially for edge cases and error handling.
- **Add Dependency Injection** Support DI frameworks (like Hilt or Dagger) for easier integration and testing.
- **Introduce UseCases** I just Implemented the code in simple way but we have to introduce use cases between Manager or Repository and ViewModel for more complex cases
- **Add Sample Fore ViewModel** we I've implemented Managers as Sample and add the viewModel in the documentation only
- **CI/CD with github actions and fastlane** I've added a sample workflow for CI/CD using GitHub Actions, but it can be enhanced with more steps like code quality checks, automated testing, and deployment.

## Common Errors & Troubleshooting
https://github.com/adhamkhwaldeh/UserBehaviorSDK/issues

## FAQs
https://github.com/adhamkhwaldeh/UserBehaviorSDK/issues

## Support
https://github.com/adhamkhwaldeh/UserBehaviorSDK
