package com.behaviosec.android.accelerometerTouchTrackerSdk.model

import android.view.MotionEvent
import java.util.Date

data class MotionEventModel(
    val event: MotionEvent,
    val date: Date
)
