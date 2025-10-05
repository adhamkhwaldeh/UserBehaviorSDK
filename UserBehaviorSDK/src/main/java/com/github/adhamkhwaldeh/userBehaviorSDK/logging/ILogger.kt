package com.github.adhamkhwaldeh.userBehaviorSDK.logging

import android.util.Log
import com.github.adhamkhwaldeh.userBehaviorSDK.config.BaseManagerConfig

/**
 * Defines a contract for a logging utility. This allows the default Android Logcat implementation
 * to be replaced by a custom logging library (e.g., Timber, a remote logger) at runtime.
 */
interface ILogger {
    fun d(tag: String, message: String, config: BaseManagerConfig)
    fun e(tag: String, message: String, config: BaseManagerConfig, throwable: Throwable? = null)
    fun w(tag: String, message: String, config: BaseManagerConfig)
    fun i(tag: String, message: String, config: BaseManagerConfig)
}