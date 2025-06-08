package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import android.view.MotionEvent
import java.util.Date

interface ActivityTouchListener {

    fun dispatchTouchEvent(event: MotionEvent, date: Date): Boolean
}