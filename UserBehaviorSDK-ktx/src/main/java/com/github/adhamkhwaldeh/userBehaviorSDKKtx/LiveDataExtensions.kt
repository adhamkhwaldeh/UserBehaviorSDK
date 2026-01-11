package com.github.adhamkhwaldeh.userBehaviorSDKKtx

import androidx.lifecycle.LiveData
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.SensorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.IErrorListener
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


//region Manager-level LiveData Extensions

//#region TouchManager
/**
 * Creates a LiveData that posts touch events from a `ITouchManager`.
 * The underlying listener is automatically registered only when the LiveData has active observers,
 * and unregistered when it becomes inactive. This is highly efficient.
 */
fun ITouchManager.touchEventsLiveData(): LiveData<MotionEventModel> {
    return object : LiveData<MotionEventModel>() {
        private val listener = object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                postValue(event)
                return true
            }
        }

        override fun onActive() {
            super.onActive()
            this@touchEventsLiveData.addListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@touchEventsLiveData.removeListener(listener)
        }
    }
}

/**
 * Creates a LiveData that emits errors from a `ITouchManager`.
 */
fun ITouchManager.errorsLiveData(): LiveData<BaseSDKException> {
    return object : LiveData<BaseSDKException>() {
        private val listener = object : TouchErrorListener {
            override fun onError(error: BaseSDKException) {
                postValue(error)
            }
        }

        override fun onActive() {
            super.onActive()
            this@errorsLiveData.addErrorListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@errorsLiveData.removeErrorListener(listener)
        }
    }
}

//endregion

//#region AccelerometerManager
/**
 * Creates a LiveData that posts accelerometer sensor data changes.
 */
fun IAccelerometerManager.sensorChangedLiveData(): LiveData<AccelerometerEventModel> {
    return object : LiveData<AccelerometerEventModel>() {
        private val listener = object : AccelerometerListener {
            override fun onSensorChanged(model: AccelerometerEventModel) {
                postValue(model)
            }
        }

        override fun onActive() {
            super.onActive()
            this@sensorChangedLiveData.addListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@sensorChangedLiveData.removeListener(listener)
        }
    }
}

fun IAccelerometerManager.accuracyChangedEventsLiveData(): LiveData<AccuracyChangedModel> {
    return object : LiveData<AccuracyChangedModel>() {
        private val listener = object : AccelerometerListener {
            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                postValue(model)
            }
        }

        override fun onActive() {
            super.onActive()
            this@accuracyChangedEventsLiveData.addListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@accuracyChangedEventsLiveData.removeListener(listener)
        }
    }
}

/**
 * Creates a LiveData that emits errors from a `IAccelerometerManager`.
 */
fun IAccelerometerManager.errorsLiveData(): LiveData<BaseSDKException> {
    return object : LiveData<BaseSDKException>() {
        private val listener = object : AccelerometerErrorListener {
            override fun onError(error: BaseSDKException) {
                postValue(error)
            }
        }

        override fun onActive() {
            super.onActive()
            this@errorsLiveData.addErrorListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@errorsLiveData.removeErrorListener(listener)
        }
    }
}

//endregion

//#region SensorsManager
/**
 * Creates a LiveData that posts sensor data changes.
 */
fun ISensorsManager.sensorChangedLiveData(): LiveData<SensorEventModel> {
    return object : LiveData<SensorEventModel>() {
        private val listener = object : SensorListener {
            override fun onSensorChanged(model: SensorEventModel) {
                postValue(model)
            }

            override fun onAccuracyChanged(model: SensorAccuracyChangedModel) { /* Do Nothing */
            }
        }

        override fun onActive() {
            super.onActive()
            this@sensorChangedLiveData.addListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@sensorChangedLiveData.removeListener(listener)
        }
    }
}

fun ISensorsManager.accuracyChangedEventsLiveData(): LiveData<SensorEventModel> {
    return object : LiveData<SensorEventModel>() {
        private val listener = object : SensorListener {
            override fun onSensorChanged(model: SensorEventModel) {
                postValue(model)
            }
        }

        override fun onActive() {
            super.onActive()
            this@accuracyChangedEventsLiveData.addListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@accuracyChangedEventsLiveData.removeListener(listener)
        }
    }
}

/**
 * Creates a LiveData that emits errors from a `ISensorsManager`.
 */
fun ISensorsManager.errorsLiveData(): LiveData<BaseSDKException> {
    return object : LiveData<BaseSDKException>() {
        private val listener = object : SensorErrorListener {
            override fun onError(error: BaseSDKException) {
                postValue(error)
            }
        }

        override fun onActive() {
            super.onActive()
            this@errorsLiveData.addErrorListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@errorsLiveData.removeErrorListener(listener)
        }
    }
}

//endregion

//endregion

//region SDK-level LiveData Extensions
/**
 * Creates a Livedata that emits all errors from any manager within the SDK.
 */
fun UserBehaviorCoreSDK.globalErrorsLiveData(): LiveData<BaseSDKException> {
    return object : LiveData<BaseSDKException>() {
        val listener = object : IErrorListener {
            override fun onError(error: BaseSDKException) {
                postValue(error)
            }
        }

        override fun onActive() {
            super.onActive()
            this@globalErrorsLiveData.addGlobalErrorListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@globalErrorsLiveData.removeGlobalErrorListener(listener)
        }
    }
}

/**
 * Creates a Live data that emits all touch events from any view-scoped manager within the SDK.
 */
fun UserBehaviorCoreSDK.globalTouchEventsLiveData(): LiveData<MotionEventModel> {
    return object : LiveData<MotionEventModel>() {
        val listener = object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                postValue(event)
                return true
            }
        }

        override fun onActive() {
            super.onActive()
            this@globalTouchEventsLiveData.addGlobalViewsListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            this@globalTouchEventsLiveData.removeGlobalViewListener(listener)
        }

    }
}

//endregion