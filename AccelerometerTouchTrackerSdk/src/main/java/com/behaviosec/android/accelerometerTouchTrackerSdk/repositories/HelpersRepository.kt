package com.behaviosec.android.accelerometerTouchTrackerSdk.repositories

import com.behaviosec.android.accelerometerTouchTrackerSdk.helpers.DateHelpers

class HelpersRepository {

    fun getCurrentDate(): java.util.Date {
        return DateHelpers.getCurrentDate()
    }

}