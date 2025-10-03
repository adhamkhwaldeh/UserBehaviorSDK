import java.net.URL

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.github.adhamkhwaldeh.userBehaviorSDK"
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
    implementation(libs.material)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.1")

    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // testImplementation(libs.konsist)
    testImplementation(libs.konsist.v0173)

}

dokka {
    moduleName.set("UserBehaviorSDK") // Or use project.name if it should be dynamic per subproject

    dokkaPublications.configureEach {
        if (this.name == "html") { // Assuming you only want to configure the 'html' publication
            outputDirectory.set(project.layout.buildDirectory.dir("dokkaDir"))
        }
    }
    // A more direct way if you only have one HTML publication (usually the case)
    // dokkaPublications.named<org.jetbrains.dokka.gradle.DokkaTaskPartial>("html") {
    //     outputDirectory.set(project.layout.buildDirectory.dir("dokkaDir"))
    // }


    // Configure the 'main' source set
    dokkaSourceSets.named("main") {
        // Only set the actual Kotlin/Java dirs once
        sourceRoots.from(project.file("src/main/java")) // Use project.file() for clarity

//        includes.from(project.file("README.md"))
        includes.from(project.file("IntegrationGuide.md"))

        skipEmptyPackages.set(true)
        // includeNonPublic.set(false) // Uncomment if needed
        // includes.from(project.file("IntegrationGuide.md")) // Uncomment if needed

        sourceLink {
            // localDirectory.set(project.file("src/main/java")) // Uncomment if needed
            remoteUrl.set(URL("https://github.com/adhamkhwaldeh/UserBehaviorSDK/tree/main/app/src/main/java").toURI())
            remoteLineSuffix.set("#L")
        }
    }

    // Configure all Dokka source sets
    dokkaSourceSets.configureEach {
        // Note: Some of these might be redundant if already set in the 'main' specific block,
        // or if you want them to apply to ALL source sets including 'main'.
        // If 'IntegrationGuide.md' is only for 'main', keep it in the 'main' block.
        // If it's for all, it's fine here.
        includes.from(project.file("IntegrationGuide.md")) // Include custom documentation

        reportUndocumented.set(true)          // Warn about undocumented public APIs
        skipDeprecated.set(true)              // Exclude deprecated elements
        suppressObviousFunctions.set(false)   // Dokka's property for 'suppress' is often more specific
        // 'suppress' itself is a boolean, but often refers to suppressing obvious functions.
        // If you meant to suppress warnings, that's different.
        // For suppressing specific warnings:
        // perPackageOption {
        //     matchingRegex.set("kotlin($|\\.).*") // Apply to all Kotlin packages
        //     suppress.set(true)
        // }


        // You've already added "src/main/java" to the 'main' source set.
        // Adding it again here for every source set might be redundant or intended.
        // If you only have one source set ('main'), this is fine.
        // If you have multiple source sets, this will add "src/main/java" to all of them.
        sourceRoots.from(project.file("src/main/java"))

        jdkVersion.set(17) // This is for the Javadoc link, not for compilation JDK.
        // For compilation, that's set in kotlinOptions or java { sourceCompatibility }

        // Example of setting platform, if needed (often auto-detected)
        // platform.set(org.jetbrains.dokka.Platform.jvm)
    }

    // pluginsConfiguration.withType<org.jetbrains.dokka.base.DokkaBase> { // This is an example, actual type might differ
    //    customStyleSheets.from(project.file("styles.css"))
    //    customAssets.from(project.file("logo.png"))
    //    footerMessage.set("(c) Your Company")
    // }

    // Dokka generates a new process managed by Gradle
    // extensions.findByType<org.jetbrains.dokka.gradle.AbstractDokkaTask>()?.let { dokkaTask ->
    //     dokkaTask.dokkaGeneratorIsolation.set(project.objects.newInstance<org.jetbrains.dokka.gradle.ProcessIsolation>().apply {
    //         maxHeapSize.set("4g")
    //     })
    // }
    // // Runs Dokka in the current Gradle process
    // // dokkaTask.dokkaGeneratorIsolation.set(project.objects.newInstance<org.jetbrains.dokka.gradle.ClassLoaderIsolation>())

}