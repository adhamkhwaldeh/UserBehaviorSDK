package com.github.adhamkhwaldeh.userBehaviorSDK.models

import android.hardware.SensorEvent
import java.util.Date

data class SensorEventModel(
    val event: SensorEvent?,
    val date: Date
)