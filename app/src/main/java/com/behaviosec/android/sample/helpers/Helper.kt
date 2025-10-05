package com.behaviosec.android.sample.helpers

import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel

object Helper {
    fun accelerometerEventMessage(model: AccelerometerEventModel): String {
        return model.toMessage()
    }

    fun accuracyChangedMessage(model: AccuracyChangedModel): String {
        return model.toMessage()
    }

    fun  managerErrorMessage(model: ManagerErrorModel):String{
        return model.toMessage()
    }

    fun  motionEventMessage(model: MotionEventModel):String{
        return model.toMessage()
    }


}