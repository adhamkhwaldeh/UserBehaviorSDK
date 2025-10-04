package com.github.adhamkhwaldeh.userBehaviorSDK.logging

import android.util.Log

/**
 * Defines a contract for a logging utility. This allows the default Android Logcat implementation
 * to be replaced by a custom logging library (e.g., Timber, a remote logger) at runtime.
 */
interface ILogger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
    fun w(tag: String, message: String)
    fun i(tag: String, message: String)
}

/**
 * The default implementation of `ILogger` which writes to Android's standard `Log` class.
 */
internal class DefaultLogger : ILogger {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }
}
