package com.github.adhamkhwaldeh.userBehaviorSDKCompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK

/**
 * CompositionLocal to provide the UserBehaviorCoreSDK instance down the composable tree.
 */
internal val LocalUserBehaviorCoreSDK = staticCompositionLocalOf<UserBehaviorCoreSDK> {
    error("No UserBehaviorCoreSDK instance provided. Make sure to wrap your composables in ProvideUserBehaviorSDK { ... }")
}

/**
 * Provides a UserBehaviorCoreSDK instance to the composition tree. All composables within the `content`
 * can access the SDK instance via `LocalUserBehaviorCoreSDK.current`.
 *
 * @param sdk The instance of the UserBehaviorCoreSDK to provide.
 * @param content The composable content that will have access to the SDK.
 */
@Composable
fun ProvideUserBehaviorSDK(
    sdk: UserBehaviorCoreSDK,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(value = LocalUserBehaviorCoreSDK.provides(sdk), content = content)
}
