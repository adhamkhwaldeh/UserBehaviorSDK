plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.behaviosec.android.sample"
    compileSdk = Integer.getInteger(libs.versions.compileSdk.get())
    compileSdkVersion =  libs.versions.compileSdkVersion.get()

    defaultConfig {
        applicationId = "com.behaviosec.android.sample"
        minSdk = Integer.getInteger(libs.versions.minSdk.get())
        targetSdk = Integer.getInteger(libs.versions.targetSdk.get())
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(":GestureDetectorSdk"))

}