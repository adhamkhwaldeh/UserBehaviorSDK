package com.behaviosec.android.userBehaviorSDK.models

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
data class AccuracyChangedModel(
    val sensor: Sensor?,
    val accuracy: Int,
    val date: Date
)
