package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

class FailToStopException (
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)