package com.github.adhamkhwaldeh.userBehaviorSDKKtx

import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel

/**
 * A sealed interface to represent the different types of successful results from the AccelerometerManager.
 * This allows a single Flow or LiveData to emit different kinds of data in a type-safe way.
 */
sealed interface AccelerometerResult {
    data class SensorChanged(val model: AccelerometerEventModel) : AccelerometerResult
    data class AccuracyChanged(val model: AccuracyChangedModel) : AccelerometerResult
}