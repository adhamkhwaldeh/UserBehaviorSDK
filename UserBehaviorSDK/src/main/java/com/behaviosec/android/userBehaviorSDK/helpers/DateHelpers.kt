package com.behaviosec.android.userBehaviorSDK.helpers

import android.icu.util.Calendar
import java.util.Date

/**
 * Date helpers
 *
 * @constructor Create empty Date helpers
 */
object DateHelpers {

    /**
     * Get current date
     *
     * @return
     */
    fun getCurrentDate(): Date {
        return Calendar.getInstance().time;
    }

}