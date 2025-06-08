package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.model.AccelerometerEventModel
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.AccuracyChangedModel

interface AccelerometerListener {
    fun onSensorChanged(model: AccelerometerEventModel) {}
    fun onAccuracyChanged(model: AccuracyChangedModel) {}
}
