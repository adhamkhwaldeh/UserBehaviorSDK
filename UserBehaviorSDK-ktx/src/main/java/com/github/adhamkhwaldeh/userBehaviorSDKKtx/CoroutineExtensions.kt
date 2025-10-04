package com.github.adhamkhwaldeh.userBehaviorSDKKtx


import androidx.lifecycle.LiveData
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

//region Manager-level Flow Extensions

/**
 * Creates a Flow that emits touch events from a `ITouchManager`.
 * The listener is automatically registered when the flow is collected and unregistered when the collection stops.
 */
fun ITouchManager.touchEvents(): Flow<MotionEventModel> = callbackFlow {
    val listener = object : TouchListener {
        override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
            trySend(event)
            return true
        }
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
}

/**
 * Creates a Flow that emits accelerometer sensor data changes.
 */
fun IAccelerometerManager.sensorChangedEvents(): Flow<AccelerometerEventModel> = callbackFlow {
    val listener = object : AccelerometerListener {
        override fun onSensorChanged(model: AccelerometerEventModel) {
            trySend(model)
        }

        override fun onAccuracyChanged(model: AccuracyChangedModel) { /* Do Nothing */
        }
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
}

/**
 * Creates a Flow that emits accelerometer accuracy changes.
 */
fun IAccelerometerManager.accuracyChangedEvents(): Flow<AccuracyChangedModel> = callbackFlow {
    val listener = object : AccelerometerListener {
        override fun onSensorChanged(model: AccelerometerEventModel) { /* Do Nothing */
        }

        override fun onAccuracyChanged(model: AccuracyChangedModel) {
            trySend(model)
        }
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
}

/**
 * Creates a Flow that emits errors from a `ITouchManager`.
 */
fun ITouchManager.errors(): Flow<ManagerErrorModel> = callbackFlow {
    val listener = object : TouchErrorListener {
        override fun onError(error: ManagerErrorModel) {
            trySend(error)
        }
    }
    addErrorListener(listener)
    awaitClose { removeErrorListener(listener) }
}

/**
 * Creates a Flow that emits errors from a `IAccelerometerManager`.
 */
fun IAccelerometerManager.errors(): Flow<ManagerErrorModel> = callbackFlow {
    val listener = object : AccelerometerErrorListener {
        override fun onError(error: ManagerErrorModel) {
            trySend(error)
        }
    }
    addErrorListener(listener)
    awaitClose { removeErrorListener(listener) }
}

//endregion

//region SDK-level Flow Extensions

/**
 * Creates a Flow that emits all errors from any manager within the SDK.
 */
fun UserBehaviorCoreSDK.globalErrors(): Flow<ManagerErrorModel> = callbackFlow {
    val listener = object : IErrorListener {
        override fun onError(error: ManagerErrorModel) {
            trySend(error)
        }
    }
    addGlobalErrorListener(listener)
    awaitClose { removeGlobalErrorListener(listener) }
}

/**
 * Creates a Flow that emits all touch events from any view-scoped manager within the SDK.
 */
fun UserBehaviorCoreSDK.globalTouchEvents(): Flow<MotionEventModel> = callbackFlow {
    val listener = object : TouchListener {
        override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
            trySend(event)
            return true
        }
    }
    addGlobalViewsListener(listener)
    awaitClose { removeGlobalViewListener(listener) }
}

//endregion