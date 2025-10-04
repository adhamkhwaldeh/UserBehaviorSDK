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


//region Manager-level Extensions

/**
 * Creates a Flow that emits a `Result` for every touch event or error from an `ITouchManager`.
 * This allows a single collector to handle both success and failure cases.
 */
fun ITouchManager.touchResultFlow(): Flow<Result<MotionEventModel>> = callbackFlow {
    val touchListener = object : TouchListener {
        override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
            trySend(Result.success(event))
            return true
        }
    }
    val errorListener = object : TouchErrorListener {
        override fun onError(error: ManagerErrorModel) {
            val exception = error.exception ?: Exception(error.message)
            trySend(Result.failure(exception))
        }
    }
    addListener(touchListener)
    addErrorListener(errorListener)
    awaitClose {
        removeListener(touchListener)
        removeErrorListener(errorListener)
    }
}

/**
 * Creates a Flow that emits a `Result` for every accelerometer event or error.
 */
fun IAccelerometerManager.accelerometerResultFlow(): Flow<Result<AccelerometerResult>> =
    callbackFlow {
        val dataListener = object : AccelerometerListener {
            override fun onSensorChanged(model: AccelerometerEventModel) {
                trySend(Result.success(AccelerometerResult.SensorChanged(model)))
            }

            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                trySend(Result.success(AccelerometerResult.AccuracyChanged(model)))
            }
        }
        val errorListener = object : AccelerometerErrorListener {
            override fun onError(error: ManagerErrorModel) {
                val exception = error.exception ?: Exception(error.message)
                trySend(Result.failure(exception))
            }
        }
        addListener(dataListener)
        addErrorListener(errorListener)
        awaitClose {
            removeListener(dataListener)
            removeErrorListener(errorListener)
        }
    }


//endregion

