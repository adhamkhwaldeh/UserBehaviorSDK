package com.behaviosec.android.accelerometerTouchTrackerSdk.helpers

import android.icu.util.Calendar
import java.util.Date

object DateHelpers {

    fun getCurrentDate(): Date {
        return Calendar.getInstance().time;
    }

}