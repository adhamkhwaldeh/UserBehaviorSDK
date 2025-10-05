package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

class DetectTouchException(
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)