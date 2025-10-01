package com.behaviosec.android.userBehaviorSDK.repositories

import com.behaviosec.android.userBehaviorSDK.helpers.DateHelpers

class HelpersRepository {

    fun getCurrentDate(): java.util.Date {
        return DateHelpers.getCurrentDate()
    }

}