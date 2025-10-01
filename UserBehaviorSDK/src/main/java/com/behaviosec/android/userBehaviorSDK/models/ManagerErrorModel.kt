package com.behaviosec.android.userBehaviorSDK.models


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
)
