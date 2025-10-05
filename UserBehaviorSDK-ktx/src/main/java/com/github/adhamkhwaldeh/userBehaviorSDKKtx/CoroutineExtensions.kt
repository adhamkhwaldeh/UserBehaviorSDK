package com.github.adhamkhwaldeh.userBehaviorSDKKtx


import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.SensorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.SensorErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.sensors.ISensorsManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorAccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorEventModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

//region Manager-level Flow Extensions

//#region TouchManager
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
//#endregion

//#region AccelerometerManager
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
//#endregion

//#region SensorsManager
/**
 * Creates a Flow that emits  sensor data changes.
 */
fun ISensorsManager.sensorChangedEvents(): Flow<SensorEventModel> = callbackFlow {
    val listener = object : SensorListener {
        override fun onSensorChanged(model: SensorEventModel) {
            trySend(model)
        }

        override fun onAccuracyChanged(model: SensorAccuracyChangedModel) { /* Do Nothing */
        }
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
}

/**
 * Creates a Flow that emits sensor accuracy changes.
 */
fun ISensorsManager.accuracyChangedEvents(): Flow<SensorAccuracyChangedModel> = callbackFlow {
    val listener = object : SensorListener {
        override fun onAccuracyChanged(model: SensorAccuracyChangedModel) {
            trySend(model)
        }
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
}

/**
 * Creates a Flow that emits errors from a `ISensorsManager`.
 */
fun ISensorsManager.errors(): Flow<ManagerErrorModel> = callbackFlow {
    val listener = object : SensorErrorListener {
        override fun onError(error: ManagerErrorModel) {
            trySend(error)
        }
    }
    addErrorListener(listener)
    awaitClose { removeErrorListener(listener) }
}
//#endregion

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

/**
 * Creates a Flow that emits all sensors events from any view-scoped manager within the SDK.
 */
fun UserBehaviorCoreSDK.globalSensorEvents(): Flow<SensorsResult> = callbackFlow {
    val listener = object : SensorListener {
        override fun onSensorChanged(model: SensorEventModel) {
            trySend(SensorsResult.SensorChanged(model))
        }

        override fun onAccuracyChanged(model: SensorAccuracyChangedModel) {
            trySend(SensorsResult.AccuracyChanged(model))
        }
    }
    addGlobalSensorListener(listener)
    awaitClose { removeGlobalSensorListener(listener) }
}

//endregion