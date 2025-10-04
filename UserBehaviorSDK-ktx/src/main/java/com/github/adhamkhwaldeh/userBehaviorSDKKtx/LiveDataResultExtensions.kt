package com.github.adhamkhwaldeh.userBehaviorSDKKtx

import androidx.lifecycle.LiveData
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
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

//region LiveData Extensions

/**
 * Creates a LiveData that posts a `Result` for every touch event or error.
 * Listeners are only active when the LiveData has active observers.
 */
fun ITouchManager.touchResultLiveData(): LiveData<Result<MotionEventModel>> {
    return object : LiveData<Result<MotionEventModel>>() {
        val touchListener = object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                postValue(Result.success(event))
                return true
            }
        }
        val errorListener = object : TouchErrorListener {
            override fun onError(error: ManagerErrorModel) {
                val exception = error.exception ?: Exception(error.message)
                postValue(Result.failure(exception))
            }
        }

        override fun onActive() {
            this@touchResultLiveData.addListener(touchListener)
            this@touchResultLiveData.addErrorListener(errorListener)
        }

        override fun onInactive() {
            this@touchResultLiveData.removeListener(touchListener)
            this@touchResultLiveData.removeErrorListener(errorListener)
        }
    }
}

/**
 * Creates a LiveData that posts a `Result` for every accelerometer event or error.
 * Listeners are only active when the LiveData has active observers.
 */
fun IAccelerometerManager.accelerometerResultLiveData(): LiveData<Result<AccelerometerResult>> {
    return object : LiveData<Result<AccelerometerResult>>() {
        val dataListener = object : AccelerometerListener {
            override fun onSensorChanged(model: AccelerometerEventModel) {
                postValue(Result.success(AccelerometerResult.SensorChanged(model)))
            }

            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                postValue(Result.success(AccelerometerResult.AccuracyChanged(model)))
            }
        }
        val errorListener = object : AccelerometerErrorListener {
            override fun onError(error: ManagerErrorModel) {
                val exception = error.exception ?: Exception(error.message)
                postValue(Result.failure(exception))
            }
        }

        override fun onActive() {
            this@accelerometerResultLiveData.addListener(dataListener)
            this@accelerometerResultLiveData.addErrorListener(errorListener)
        }

        override fun onInactive() {
            this@accelerometerResultLiveData.removeListener(dataListener)
            this@accelerometerResultLiveData.removeErrorListener(errorListener)
        }
    }
}

//endregion