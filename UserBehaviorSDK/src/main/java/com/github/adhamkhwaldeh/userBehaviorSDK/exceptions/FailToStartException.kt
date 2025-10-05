package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

class FailToStartException(
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)