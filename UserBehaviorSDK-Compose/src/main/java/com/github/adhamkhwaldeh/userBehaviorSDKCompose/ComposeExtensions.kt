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
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.sensors.ISensorsManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerSensorKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.touchResultFlow


/**
 * A Composable function that remembers an `ITouchManager` scoped to the current Activity.
 * Its lifecycle is automatically bound to the composition.
 *
 * This function retrieves the SDK instance from `LocalUserBehaviorCoreSDK`.
 *
 * @return A remembered instance of `ITouchManager`.
 * @throws IllegalStateException if the current context is not an Activity.
 */
@Composable
fun rememberActivityTouchManager(): ITouchManager {
    val context = LocalContext.current
    val activity =
        context as? Activity ?: throw IllegalStateException("Current context is not an Activity")

    val sdk = LocalUserBehaviorCoreSDK.current
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
 * This function retrieves the SDK instance from `LocalUserBehaviorCoreSDK`.
 *
 * @param enabled A boolean to conditionally enable or disable the event collection.
 * @param onEvent A callback that will be invoked with a `Result` for each touch event or error.
 * @return A `Modifier` instance.
 */
fun Modifier.collectViewTouchEvents(
    enabled: Boolean = true,
    onEvent: (Result<MotionEventModel>) -> Unit
): Modifier = composed {
    if (enabled) {
        val view = LocalView.current
        val sdk = LocalUserBehaviorCoreSDK.current
        val manager = remember(view) { sdk.fetchOrCreateViewTouchManager(view) }

        // Manage the lifecycle of the manager
        DisposableEffect(manager,view) {
            manager.start()
            onDispose {
                manager.stop()
            }
        }

        // Collect the flow of events
        LaunchedEffect(manager, view) {
            manager.touchResultFlow().collect { result ->
                onEvent(result)
            }
        }
    }
    this
}

/**
 * A Composable function that remembers an `IAccelerometerManager` instance and binds its lifecycle
 * to the current composition.
 *
 * This function retrieves the SDK instance from `LocalUserBehaviorCoreSDK`.
 *
 * @return A remembered instance of `IAccelerometerManager`.
 */
@Composable
fun rememberAccelerometerManager(): IAccelerometerManager {
    val sdk = LocalUserBehaviorCoreSDK.current
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
 * A Composable function that remembers an `IAccelerometerManager` instance and binds its lifecycle
 * to the current composition.
 *
 * This function retrieves the SDK instance from `LocalUserBehaviorCoreSDK`.
 *
 * @return A remembered instance of `IAccelerometerManager`.
 */
@Composable
fun rememberSensorManager(sensorType: ManagerSensorKey): ISensorsManager {
    val sdk = LocalUserBehaviorCoreSDK.current
    val manager = remember { sdk.fetchOrCreateSensorManager(sensorType) }

    DisposableEffect(manager) {
        manager.start()
        onDispose {
            manager.stop()
        }
    }
    return manager
}

