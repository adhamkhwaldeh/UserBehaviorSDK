package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

class SensorNotAvailableException(
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)