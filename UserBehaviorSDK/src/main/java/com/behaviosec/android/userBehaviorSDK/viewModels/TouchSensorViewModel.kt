package com.behaviosec.android.userBehaviorSDK.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.behaviosec.android.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.TouchListener
import com.behaviosec.android.userBehaviorSDK.managers.AccelerometerManager
import com.behaviosec.android.userBehaviorSDK.managers.touchs.ActivityTouchManager
import com.behaviosec.android.userBehaviorSDK.models.AccelerometerEventModel
import com.behaviosec.android.userBehaviorSDK.models.AccuracyChangedModel
import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel
import com.behaviosec.android.userBehaviorSDK.models.MotionEventModel
import kotlinx.coroutines.launch

/**
 * Touch sensor view model
 *
 * @property accelerometerManager
 * @property activityTouchManager
 * @constructor Create empty Touch sensor view model
 */
class TouchSensorViewModel(
    private val accelerometerManager: AccelerometerManager,
    private val activityTouchManager: ActivityTouchManager
) : ViewModel() {

    private val _lastAccelerometerEvent = MutableLiveData<AccelerometerEventModel>()
    val lastAccelerometerEvent: LiveData<AccelerometerEventModel> = _lastAccelerometerEvent

    private val _lastAccuracyEvent = MutableLiveData<AccuracyChangedModel>()
    val lastAccuracyEvent: LiveData<AccuracyChangedModel> = _lastAccuracyEvent

    private val _accelerometerError = MutableLiveData<ManagerErrorModel>()
    val accelerometerError: LiveData<ManagerErrorModel> = _accelerometerError


    private val _lastMotionEvent = MutableLiveData<MotionEventModel>()
    val lastMotionEvent: LiveData<MotionEventModel> = _lastMotionEvent

    private val _motionError = MutableLiveData<ManagerErrorModel>()
    val motionError: LiveData<ManagerErrorModel> = _motionError

    init {
        accelerometerManager.addListener(object : AccelerometerListener {
            override fun onSensorChanged(model: AccelerometerEventModel) {
                super.onSensorChanged(model)
                _lastAccelerometerEvent.postValue(model)
            }

            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                super.onAccuracyChanged(model)
                _lastAccuracyEvent.postValue(model)
            }
        })

        accelerometerManager.addErrorListener(object : AccelerometerErrorListener {
            override fun onError(error: ManagerErrorModel) {
                _accelerometerError.postValue(error)
            }
        })

        activityTouchManager.addListener(object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                _lastMotionEvent.postValue(event)
                return super.dispatchTouchEvent(event)
            }
        })

        activityTouchManager.addErrorListener(object : TouchErrorListener {
            override fun onError(error: ManagerErrorModel) {
                _motionError.postValue(error)
            }
        })
    }

    /**
     * Start tracking
     *
     */
    fun startTracking() {
        viewModelScope.launch {
            accelerometerManager.start()
            activityTouchManager.setEnabled(true)
        }
    }

    /**
     * Stop tracking
     *
     */
    fun stopTracking() {
        viewModelScope.launch {
            accelerometerManager.stop()
            activityTouchManager.setEnabled(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTracking()
    }

}
