package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException

class FailToCreateActivityManagerException (
    override val message: String?,
    override val cause: Throwable? = null
) : BaseSDKException(message, cause)