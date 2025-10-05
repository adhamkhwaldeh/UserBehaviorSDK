package com.behaviosec.android.sample.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig

import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.AccelerometerResult
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accelerometerResultFlow
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accelerometerResultLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accuracyChangedEvents
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.errors
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.sensorChangedEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CoroutineViewModel(
    private val userBehaviorCoreSDK: UserBehaviorCoreSDK
) : ViewModel() {

    //#region Accelerometer
    private val accelerometerManager: IAccelerometerManager =
        userBehaviorCoreSDK.getAccelerometerManager(AccelerometerConfig())

    private val _lastAccelerometerEvent = MutableStateFlow<AccelerometerEventModel?>(null)
    val lastAccelerometerEvent: StateFlow<AccelerometerEventModel?> = _lastAccelerometerEvent
    private val _lastAccuracyEvent = MutableStateFlow<AccuracyChangedModel?>(null)
    val lastAccuracyEvent: StateFlow<AccuracyChangedModel?> = _lastAccuracyEvent

    private val _accelerometerError = MutableStateFlow<ManagerErrorModel?>(null)
    val accelerometerError: StateFlow<ManagerErrorModel?> = _accelerometerError

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

    /**
     * Clean up resources when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        // Ensure the manager is stopped to prevent resource leaks
        stopTracking()
    }
}
