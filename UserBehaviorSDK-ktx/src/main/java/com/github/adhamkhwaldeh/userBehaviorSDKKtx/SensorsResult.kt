package com.github.adhamkhwaldeh.userBehaviorSDKKtx

import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorAccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorEventModel

/**
 * A sealed interface to represent the different types of successful results from the SensorsManager.
 * This allows a single Flow or LiveData to emit different kinds of data in a type-safe way.
 */
sealed interface SensorsResult {
    data class SensorChanged(val model: SensorEventModel) : SensorsResult
    data class AccuracyChanged(val model: SensorAccuracyChangedModel) : SensorsResult
}