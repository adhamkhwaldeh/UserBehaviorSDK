package com.github.adhamkhwaldeh.userBehaviorSDKCompose

//import androidx.annotation.RequiresOptIn

/**
 * Marks declarations that are part of the experimental UserBehaviorSDK Compose extensions.
 * These APIs are subject to change or removal in future releases.
 *
 * It is preferable to use the coroutine extensions from the `userBehaviorSDK-ktx` module for now.
 */


@kotlin.RequiresOptIn(
    message = "This API is experimental and may change in the future .\n It is preferable to use the coroutine extensions from the `userBehaviorSDK-ktx` module for now.",
    level = kotlin.RequiresOptIn.Level.WARNING,
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
)
annotation class ExperimentalUserBehaviorSDKComposeApi
