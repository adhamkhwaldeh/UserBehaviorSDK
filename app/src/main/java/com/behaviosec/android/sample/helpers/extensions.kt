package com.behaviosec.android.sample.helpers

import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel

fun AccelerometerEventModel.toMessage(): String {
    return "Sensor changed: " + (event?.values?.get(0)
        ?: "") + ", " + (event?.values?.get(1) ?: "") + ", " +
            (event?.values?.get(2) ?: "") + " at " + date
}

fun AccuracyChangedModel.toMessage(): String {
    return "Accuracy changed: " + accuracy + " at " + date
}

fun ManagerErrorModel.toMessage(): String {
    return "Error: " + this.message
}

fun MotionEventModel.toMessage(): String {
    //"Touch event: " + model.getEvent() + " at " + model.getDate()
    return "Touch event: " + event.x + ", " + event.y + "action " + event.action + " at " + date
}