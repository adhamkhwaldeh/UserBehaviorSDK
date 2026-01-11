package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException

class FailToStopException (
    override val message: String?,
    override val cause: Throwable? = null
) : BaseSDKException(message, cause)