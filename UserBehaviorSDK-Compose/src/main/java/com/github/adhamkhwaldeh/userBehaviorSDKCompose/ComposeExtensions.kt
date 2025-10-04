package com.github.adhamkhwaldeh.userBehaviorSDKCompose

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel

/**
 * NOTE: To use these extensions, you will need to add Compose dependencies to this module's
 * `build.gradle.kts` file and expose them as `api` dependencies:
 *
 * ```

 * ```
 */

/**
 * A Composable function that remembers an `IAccelerometerManager` instance and binds its lifecycle
 * to the current composition.
 *
 * The manager will automatically be started when the composable enters the composition and stopped
 * when it leaves.
 *
 * @return A remembered instance of `IAccelerometerManager`.
 */
@Composable
fun rememberAccelerometerManager(): IAccelerometerManager {
    val context = LocalContext.current
    val sdk = remember { UserBehaviorCoreSDK.getInstance(context) }
    val manager = remember { sdk.getAccelerometerManager() }

    DisposableEffect(manager) {
        manager.start()
        onDispose {
            manager.stop()
        }
    }
    return manager
}

/**
 * A Composable function that remembers an `ITouchManager` scoped to the current Activity.
 * Its lifecycle is automatically bound to the composition.
 *
 * @return A remembered instance of `ITouchManager`.
 * @throws IllegalStateException if the current context is not an Activity.
 */
@Composable
fun rememberActivityTouchManager(): ITouchManager {
    val context = LocalContext.current
    val activity = context as? Activity ?: throw IllegalStateException("Current context is not an Activity")

    val sdk = remember(context) { UserBehaviorCoreSDK.getInstance(context) }
    val manager = remember(activity) { sdk.fetchOrCreateActivityTouchManager(activity) }

    DisposableEffect(manager) {
        manager.start()
        onDispose {
            manager.stop()
        }
    }
    return manager
}

/**
 * A Modifier that collects touch events for the Composable it is applied to. It automatically
 * creates and manages a `ViewTouchManager` scoped to the underlying View.
 *
 * This is the idiomatic way to collect view-specific touch data in Compose.
 *
 * @param onEvent A callback that will be invoked with a `Result` for each touch event or error.
 * @return A `Modifier` instance.
 */
fun Modifier.collectViewTouchEvents(onEvent: (Result<MotionEventModel>) -> Unit): Modifier = composed {
    val view = LocalView.current
    val sdk = remember(view) { UserBehaviorCoreSDK.getInstance(view.context) }
    val manager = remember(view) { sdk.fetchOrCreateViewTouchManager(view) }

    // Manage the lifecycle of the manager
    DisposableEffect(manager) {
        manager.start()
        onDispose {
            manager.stop()
        }
    }

    // Collect the flow of events
    LaunchedEffect(manager) {
        manager.touchResultFlow().collect { result ->
            onEvent(result)
        }
    }

    this
}
