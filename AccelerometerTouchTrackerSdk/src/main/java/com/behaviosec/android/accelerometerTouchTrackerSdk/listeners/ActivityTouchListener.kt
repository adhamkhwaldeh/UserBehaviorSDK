package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.model.MotionEventModel

interface ActivityTouchListener {
    fun dispatchTouchEvent(model: MotionEventModel): Boolean{
        return true //return true to continue executing the base or original event
    }
}
