package com.github.adhamkhwaldeh.userBehaviorSDKKtx

import androidx.lifecycle.LiveData
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseUserBehaviorException
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.SensorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.SensorErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.sensors.ISensorsManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorAccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorEventModel

//region Manager-level Extensions

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
            override fun onError(error: BaseUserBehaviorException) {
//                val exception = error.cause ?: Exception(error.message)
                postValue(Result.failure(error))
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
            override fun onError(error: BaseUserBehaviorException) {
                val exception = error.cause ?: Exception(error.message)
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

/**
 * Creates a LiveData that posts a `Result` for every accelerometer event or error.
 * Listeners are only active when the LiveData has active observers.
 */
fun ISensorsManager.sensorResultLiveData(): LiveData<Result<SensorsResult>> {
    return object : LiveData<Result<SensorsResult>>() {
        val dataListener = object : SensorListener {
            override fun onSensorChanged(model: SensorEventModel) {
                postValue(Result.success(SensorsResult.SensorChanged(model)))
            }

            override fun onAccuracyChanged(model: SensorAccuracyChangedModel) {
                postValue(Result.success(SensorsResult.AccuracyChanged(model)))
            }
        }
        val errorListener = object : SensorErrorListener {
            override fun onError(error: BaseUserBehaviorException) {
                val exception = error.cause ?: Exception(error.message)
                postValue(Result.failure(exception))
            }
        }

        override fun onActive() {
            this@sensorResultLiveData.addListener(dataListener)
            this@sensorResultLiveData.addErrorListener(errorListener)
        }

        override fun onInactive() {
            this@sensorResultLiveData.removeListener(dataListener)
            this@sensorResultLiveData.removeErrorListener(errorListener)
        }
    }
}
//endregion