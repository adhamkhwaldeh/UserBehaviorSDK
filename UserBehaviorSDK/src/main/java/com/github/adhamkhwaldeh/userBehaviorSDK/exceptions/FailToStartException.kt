package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseUserBehaviorException

class FailToStartException(
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)