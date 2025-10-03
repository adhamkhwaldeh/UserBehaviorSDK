package com.github.adhamkhwaldeh.userBehaviorSDK.models


/**
 * Manager error model
 *
 * @property exception
 * @property message
 * @constructor Create empty Manager error model
 */
data class ManagerErrorModel(
    val exception: Exception? = null,
    val message: String

) {
    companion object {
        fun fromException(exception: Exception): ManagerErrorModel {
            return ManagerErrorModel(exception, exception.message ?: "Unknown error")
        }
    }
}
