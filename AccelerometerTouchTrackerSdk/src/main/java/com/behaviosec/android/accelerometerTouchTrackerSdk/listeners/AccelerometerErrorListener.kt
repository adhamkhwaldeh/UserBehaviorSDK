package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.model.ManagerErrorModel

interface AccelerometerErrorListener {
    fun onAccelerometerError(error: ManagerErrorModel)
}
