package com.behaviosec.android.sample.viewModels

import androidx.activity.result.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.AccelerometerResult
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accelerometerResultLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accuracyChangedEvents
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accuracyChangedEventsLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.errorsLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.sensorChangedLiveData
import kotlinx.coroutines.launch

/**
 * LiveData-based ViewModel for sensor data.
 *
 * @property userBehaviorCoreSDK The main SDK entry point injected by Koin.
 */
class LiveDataViewModel(
    private val userBehaviorCoreSDK: UserBehaviorCoreSDK
) : ViewModel() {

    //#region Accelerometer
    private val accelerometerManager: IAccelerometerManager =
        userBehaviorCoreSDK.getAccelerometerManager(AccelerometerConfig())

    val lastAccelerometerEvent: LiveData<AccelerometerEventModel> =
        accelerometerManager.sensorChangedLiveData()

    val lastAccuracyEvent: LiveData<AccuracyChangedModel> =
        accelerometerManager.accuracyChangedEventsLiveData()

    val accelerometerError: LiveData<ManagerErrorModel> =
        accelerometerManager.errorsLiveData()

    val accelerometerResult: LiveData<Result<AccelerometerResult>> =
        accelerometerManager.accelerometerResultLiveData()

    /**
     * Starts tracking accelerometer data.
     */
    fun startTracking() {
        viewModelScope.launch {
            // It's good practice to enable logging for debugging
            accelerometerManager
                .setLoggingEnabled(true)
                .setDebugMode(true)
            accelerometerManager.start()
        }
    }

    /**
     * Stops tracking accelerometer data.
     */
    fun stopTracking() {
        viewModelScope.launch {
            accelerometerManager.stop()
        }
    }

    //#endregion


    /**
     * Clean up resources when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        // Ensure the manager is stopped to prevent leaks
        stopTracking()
    }

//    private val _lastAccelerometerEvent = MutableLiveData<AccelerometerEventModel>()
//    val lastAccelerometerEvent: LiveData<AccelerometerEventModel> = _lastAccelerometerEvent
//
//    private val _lastAccuracyEvent = MutableLiveData<AccuracyChangedModel>()
//    val lastAccuracyEvent: LiveData<AccuracyChangedModel> = _lastAccuracyEvent
//
//    private val _accelerometerError = MutableLiveData<ManagerErrorModel>()
//    val accelerometerError: LiveData<ManagerErrorModel> = _accelerometerError
//
//
//    private val _lastMotionEvent = MutableLiveData<MotionEventModel>()
//    val lastMotionEvent: LiveData<MotionEventModel> = _lastMotionEvent
//
//    private val _motionError = MutableLiveData<ManagerErrorModel>()
//    val motionError: LiveData<ManagerErrorModel> = _motionError
//
//    init {
//        accelerometerManager.addListener(object : AccelerometerListener {
//            override fun onSensorChanged(model: AccelerometerEventModel) {
//                super.onSensorChanged(model)
//                _lastAccelerometerEvent.postValue(model)
//            }
//
//            override fun onAccuracyChanged(model: AccuracyChangedModel) {
//                super.onAccuracyChanged(model)
//                _lastAccuracyEvent.postValue(model)
//            }
//        })
//
//        accelerometerManager.addErrorListener(object : AccelerometerErrorListener {
//            override fun onError(error: ManagerErrorModel) {
//                _accelerometerError.postValue(error)
//            }
//        })
//
//        activityTouchManager.addListener(object : TouchListener {
//            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
//                _lastMotionEvent.postValue(event)
//                return super.dispatchTouchEvent(event)
//            }
//        })
//
//        activityTouchManager.addErrorListener(object : TouchErrorListener {
//            override fun onError(error: ManagerErrorModel) {
//                _motionError.postValue(error)
//            }
//        })
//    }
//
//    /**
//     * Start tracking
//     *
//     */
//    fun startTracking() {
//        viewModelScope.launch {
//            accelerometerManager.start()
//            activityTouchManager.setEnabled(true)
//        }
//    }
//
//    /**
//     * Stop tracking
//     *
//     */
//    fun stopTracking() {
//        viewModelScope.launch {
//            accelerometerManager.stop()
//            activityTouchManager.setEnabled(false)
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        stopTracking()
//    }

}