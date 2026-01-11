package com.behaviosec.android.sample.helpers

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorEventModel

// Simple extension function for formatting the float values
private fun Float.format(): String = String.format("%.2f", this)

fun AccelerometerEventModel.toMessage(): String {
    if (this.event == null) {
        return "ACC: IDLE"
    } else {
        val values = event!!.values
        return "ACC: X=${values[0].format()}, Y=${values[1].format()}, Z=${values[2].format()}"
    }
//    return "ACC: X=" + (event?.values?.get(0)
//        ?: "") + ", " + (event?.values?.get(1) ?: "") + ", " +
//            (event?.values?.get(2) ?: "") + " at " + date
}

fun AccuracyChangedModel.toMessage(): String {
    return "Accuracy changed: " + accuracy + " at " + date
}

fun BaseSDKException.toMessage(): String {
    return "Error: " + this.message
}

fun MotionEventModel.toMessage(): String {
    //"Touch event: " + model.getEvent() + " at " + model.getDate()
    return "Touch event: " + event.x + ", " + event.y + " action " + event.action + " at " + date
}

fun SensorEventModel.toMessage(): String {
    if (this.event == null) {
        return "ACC: IDLE"
    } else {
        val values = event!!.values
        return "ACC: X=${values[0].format()}, Y=${values[1].format()}, Z=${values[2].format()}"
    }
}
