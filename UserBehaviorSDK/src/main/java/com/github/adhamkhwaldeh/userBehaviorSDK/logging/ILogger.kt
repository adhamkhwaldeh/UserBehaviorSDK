package com.github.adhamkhwaldeh.userBehaviorSDK.logging

import com.github.adhamkhwaldeh.userBehaviorSDK.config.BaseBehaviorConfig

/**
 * Defines a contract for a logging utility. This allows the default Android Logcat implementation
 * to be replaced by a custom logging library (e.g., Timber, a remote logger) at runtime.
 */
interface ILogger {
    fun d(tag: String, message: String, config: BaseBehaviorConfig)
    fun e(tag: String, message: String, config: BaseBehaviorConfig, throwable: Throwable? = null)
    fun w(tag: String, message: String, config: BaseBehaviorConfig)
    fun i(tag: String, message: String, config: BaseBehaviorConfig)
}