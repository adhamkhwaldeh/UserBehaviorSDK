package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

class DefaultUserBehaviorException (
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)