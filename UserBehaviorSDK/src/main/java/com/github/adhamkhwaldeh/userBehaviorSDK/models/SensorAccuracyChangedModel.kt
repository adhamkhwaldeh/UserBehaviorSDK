package com.github.adhamkhwaldeh.userBehaviorSDK.models

import android.hardware.Sensor
import java.util.Date

data class SensorAccuracyChangedModel(
    val sensor: Sensor?,
    val accuracy: Int,
    val date: Date
)