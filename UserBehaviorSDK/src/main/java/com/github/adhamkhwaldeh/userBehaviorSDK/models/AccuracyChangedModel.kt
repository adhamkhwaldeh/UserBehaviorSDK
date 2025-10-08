package com.github.adhamkhwaldeh.userBehaviorSDK.models

import android.hardware.Sensor
import java.util.Date


/**
 * Accuracy changed model
 *
 * @property sensor
 * @property accuracy
 * @property date
 * @constructor Create empty Accuracy changed model
 */

@Deprecated(
    "This manager is deprecated. It is recommended to use Android's SensorAccuracyChangedModel directly.",
    ReplaceWith("SensorAccuracyChangedModel")
)
data class AccuracyChangedModel(
    val sensor: Sensor?,
    val accuracy: Int,
    val date: Date
)
