package com.behaviosec.android.accelerometerTouchTrackerSdk.model

import android.hardware.SensorEvent
import java.util.Date

data class AccelerometerEventModel(
    val event: SensorEvent,
    val date: Date
)
