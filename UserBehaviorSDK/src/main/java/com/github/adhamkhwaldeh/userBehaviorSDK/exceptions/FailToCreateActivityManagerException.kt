package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

class FailToCreateActivityManagerException (
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)