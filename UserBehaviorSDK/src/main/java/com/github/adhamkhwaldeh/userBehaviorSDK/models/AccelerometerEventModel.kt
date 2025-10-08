package com.github.adhamkhwaldeh.userBehaviorSDK.models

import android.hardware.SensorEvent
import java.util.Date


/**
 * Accelerometer event model
 *
 * @property event
 * @property date
 * @constructor Create empty Accelerometer event model
 */

@Deprecated(
    "This manager is deprecated. It is recommended to use Android's SensorEventModel directly.",
    ReplaceWith("SensorEventModel")
)
data class AccelerometerEventModel(
    val event: SensorEvent?,
    val date: Date
)
