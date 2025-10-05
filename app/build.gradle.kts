plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.behaviosec.android.sample"
    compileSdk = libs.versions.compileSdk.get().toInt()
    compileSdkVersion = libs.versions.compileSdkVersion.get()

    defaultConfig {
        applicationId = "com.behaviosec.android.sample"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
    composeOptions {
        // This version must be compatible with your project's Kotlin version.
        // For Kotlin 1.9.24, the correct Compose Compiler version is 1.5.12.
        kotlinCompilerExtensionVersion = "1.5.12"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.lifecycle.runtime.compose)
    // ADD THIS LINE for Material Design 3 components
    implementation(libs.androidx.compose.material3)
    // ADD THIS for the @Preview annotation
    implementation(libs.androidx.compose.ui.tooling.preview)

    // AND ADD THIS for the IDE to render the preview
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation("io.insert-koin:koin-android:3.5.3")

    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.orhanobut:logger:2.2.0")


    implementation(project(":UserBehaviorSDK"))
    implementation(project(":UserBehaviorSDK-ktx"))
    implementation(project(":UserBehaviorSDK-Compose"))
//    implementation(libs.userbehaviorsdk)

}