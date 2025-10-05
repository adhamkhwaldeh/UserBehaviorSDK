package com.github.adhamkhwaldeh.userBehaviorSDK.models


/**
 * Manager error model
 *
 * @property exception
 * @property message
 * @constructor Create empty Manager error model
 */
data class ManagerErrorModel(
    val exception: Throwable? = null,
    val message: String

) {
    companion object {
        fun fromException(exception: Exception): ManagerErrorModel {
            return ManagerErrorModel(exception, exception.message ?: "Unknown error")
        }

        @JvmStatic
        fun fromException(throwable: Throwable): ManagerErrorModel { // Changed from Exception to Throwable
            return ManagerErrorModel(
                message = throwable.message ?: "An unknown error occurred",
                exception = throwable  // Storing the original throwable is good practice
            )
        }
    }
}
