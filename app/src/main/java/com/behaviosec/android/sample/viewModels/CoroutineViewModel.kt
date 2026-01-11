package com.behaviosec.android.sample.viewModels


import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.AccelerometerResult
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accelerometerResultFlow
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accelerometerResultLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accuracyChangedEvents
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.errors
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.sensorChangedEvents
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.touchResultFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CoroutineViewModel(
    private val userBehaviorCoreSDK: UserBehaviorCoreSDK
) : ViewModel() {

    //#region Accelerometer
    private val accelerometerManager: IAccelerometerManager =
        userBehaviorCoreSDK.getAccelerometerManager()

    private val _lastAccelerometerEvent = MutableStateFlow<AccelerometerEventModel?>(null)
    val lastAccelerometerEvent: StateFlow<AccelerometerEventModel?> = _lastAccelerometerEvent
    private val _lastAccuracyEvent = MutableStateFlow<AccuracyChangedModel?>(null)
    val lastAccuracyEvent: StateFlow<AccuracyChangedModel?> = _lastAccuracyEvent

    private val _accelerometerError = MutableStateFlow<BaseSDKException?>(null)
    val accelerometerError: StateFlow<BaseSDKException?> = _accelerometerError

    private val _accelerometerResult = MutableStateFlow<Result<AccelerometerResult>?>(null)
    val accelerometerResult: StateFlow<Result<AccelerometerResult>?> = _accelerometerResult

    init {
        // 3. Collect from the KTX flows and update the StateFlows
        viewModelScope.launch {
            accelerometerManager.sensorChangedEvents().collect {
                _lastAccelerometerEvent.value = it
            }
        }
        viewModelScope.launch {
            accelerometerManager.accuracyChangedEvents().collect {
                _lastAccuracyEvent.value = it
            }
        }
        viewModelScope.launch {
            accelerometerManager.errors().collect {
                _accelerometerError.value = it
            }
        }

        viewModelScope.launch {
            accelerometerManager.accelerometerResultFlow().collect {
                _accelerometerResult.value = it
            }
        }


    }

    /**
     * Starts tracking accelerometer data.
     */
    fun startTracking() {
        viewModelScope.launch {
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

    //#region Touch Managers
    private var activityTouchManager: ITouchManager? = null
    private var viewTouchManager: ITouchManager? = null

    private val _activityTouchResult = MutableStateFlow<Result<MotionEventModel>?>(null)
    val activityTouchResult: StateFlow<Result<MotionEventModel>?> = _activityTouchResult

    private val _viewTouchResult = MutableStateFlow<Result<MotionEventModel>?>(null)
    val viewTouchResult: StateFlow<Result<MotionEventModel>?> = _viewTouchResult

    private var activityJob: Job? = null
    private var viewJob: Job? = null

    fun startActivityTouchTracking(activity: Activity) {
        activityJob?.cancel() // Cancel previous job if any
        activityTouchManager =
            userBehaviorCoreSDK.fetchOrCreateActivityTouchManager(activity)
        activityJob = viewModelScope.launch {
            activityTouchManager?.touchResultFlow()?.collect {
                _activityTouchResult.value = it
            }
        }
        activityTouchManager?.start()
    }

    fun stopActivityTouchTracking() {
        activityJob?.cancel()
        activityTouchManager?.stop()
        activityTouchManager = null
    }

    fun startViewTouchTracking(view: View) {
        viewJob?.cancel() // Cancel previous job if any
        viewTouchManager = userBehaviorCoreSDK.fetchOrCreateViewTouchManager(view)
        viewJob = viewModelScope.launch {
            viewTouchManager?.touchResultFlow()?.collect {
                _viewTouchResult.value = it
            }
        }
        viewTouchManager?.start()
    }

    fun stopViewTouchTracking() {
        viewJob?.cancel()
        viewTouchManager?.stop()
        viewTouchManager = null
    }
    //#endregion

    /**
     * Clean up resources when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        // Ensure all managers are stopped to prevent resource leaks
        stopTracking()
        stopActivityTouchTracking()
        stopViewTouchTracking()
    }
}
