package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseUserBehaviorException

class DetectTouchException(
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)