package com.behaviosec.android.accelerometerTouchTrackerSdk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerErrorListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.ActivityTouchErrorListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.ActivityTouchListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AccelerometerManager
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.ActivityTouchManager
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.AccelerometerEventModel
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.AccuracyChangedModel
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.ManagerErrorModel
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.MotionEventModel
import kotlinx.coroutines.launch

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
            override fun onAccelerometerError(error: ManagerErrorModel) {
                _accelerometerError.postValue(error)
            }
        })

        activityTouchManager.setListener(object : ActivityTouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                _lastMotionEvent.postValue(event)
                return super.dispatchTouchEvent(event)
            }
        })

        activityTouchManager.setErrorListener(object : ActivityTouchErrorListener {
            override fun onActivityTouchError(error: ManagerErrorModel) {
                _motionError.postValue(error)
            }
        })
    }

    fun startTracking() {
        viewModelScope.launch {
            accelerometerManager.start()
            activityTouchManager.setEnabled(true)
        }
    }

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
