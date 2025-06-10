package com.behaviosec.android.accelerometerTouchTrackerSdk.models

import android.view.MotionEvent
import java.util.Date


/**
 * Motion event model
 *
 * @property event
 * @property date
 * @constructor Create empty Motion event model
 */
data class MotionEventModel(
    val event: MotionEvent,
    val date: Date
)
