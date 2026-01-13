package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorAccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorEventModel

interface SensorListener : CallbackListener {
    /**
     * On sensor changed
     *
     * @param model
     */
    fun onSensorChanged(model: SensorEventModel){}

    /**
     * On accuracy changed
     *
     * @param model
     */
    fun onAccuracyChanged(model: SensorAccuracyChangedModel){}
}