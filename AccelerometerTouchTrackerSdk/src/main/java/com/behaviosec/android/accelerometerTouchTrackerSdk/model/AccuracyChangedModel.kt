package com.behaviosec.android.accelerometerTouchTrackerSdk.model

import android.hardware.Sensor
import java.util.Date

data class AccuracyChangedModel(
    val sensor: Sensor?,
    val accuracy: Int,
    val date: Date
)
