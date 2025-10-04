package com.github.adhamkhwaldeh.userBehaviorSDKKtx

import androidx.lifecycle.LiveData
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


//region Manager-level LiveData Extensions

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
 * Creates a LiveData that posts accelerometer sensor data changes.
 */
fun IAccelerometerManager.sensorChangedLiveData(): LiveData<AccelerometerEventModel> {
    return object : LiveData<AccelerometerEventModel>() {
        private val listener = object : AccelerometerListener {
            override fun onSensorChanged(model: AccelerometerEventModel) {
                postValue(model)
            }

            override fun onAccuracyChanged(model: AccuracyChangedModel) { /* Do Nothing */
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

//endregion

//region SDK-level LiveData Extensions
/**
 * Creates a Livedata that emits all errors from any manager within the SDK.
 */
fun UserBehaviorCoreSDK.globalErrorsLiveData(): LiveData<ManagerErrorModel> {
    return object : LiveData<ManagerErrorModel>() {
        val listener = object : IErrorListener {
            override fun onError(error: ManagerErrorModel) {
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