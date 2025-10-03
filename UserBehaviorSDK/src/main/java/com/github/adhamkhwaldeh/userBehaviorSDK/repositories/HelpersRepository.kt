package com.github.adhamkhwaldeh.userBehaviorSDK.repositories

import com.github.adhamkhwaldeh.userBehaviorSDK.helpers.DateHelpers
import java.util.Date

class HelpersRepository {

    fun getCurrentDate(): Date {
        return DateHelpers.getCurrentDate()
    }

}