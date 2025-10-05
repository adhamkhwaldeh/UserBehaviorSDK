package com.github.adhamkhwaldeh.userBehaviorSDK.exceptions

abstract class BaseUserBehaviorException(
    override val message: String?,
    override val cause: Throwable? = null
) : Throwable(message, cause), IUserBehaviorException {
    companion object {
        @JvmStatic
        fun fromException(throwable: Throwable): BaseUserBehaviorException { // Changed from Exception to Throwable
            return DefaultUserBehaviorException(
                message = throwable.message ?: "An unknown error occurred",
                cause = throwable  // Storing the original throwable is good practice
            )
        }
    }
}