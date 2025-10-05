plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.github.adhamkhwaldeh.userBehaviorSDKCompose"
    compileSdk = libs.versions.compileSdk.get().toInt()
    compileSdkVersion = libs.versions.compileSdkVersion.get()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        minSdkVersion(libs.versions.minSdk.get().toInt())

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.lifecycle.runtime.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":UserBehaviorSDK"))
    implementation(project(":UserBehaviorSDK-ktx"))
}