plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.github.adhamkhwaldeh.userBehaviorSDKKtx"
    compileSdk = Integer.getInteger(libs.versions.compileSdk.get())
    compileSdkVersion = libs.versions.compileSdkVersion.get()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        minSdkVersion(libs.versions.minSdk.get().toInt())
//        targetSdk = Integer.getInteger(libs.versions.targetSdk.toString())
        version = "1.0.0"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17) // This aligns both Java and Kotlin to JDK 17
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
//    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation(libs.commonsdk)
    implementation(project(":UserBehaviorSDK"))
}