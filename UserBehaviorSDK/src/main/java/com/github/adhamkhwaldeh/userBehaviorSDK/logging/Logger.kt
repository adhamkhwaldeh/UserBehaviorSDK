package com.github.adhamkhwaldeh.userBehaviorSDK.logging

/**
 * A static proxy object for logging. This object holds a reference to an `ILogger` instance
 * and forwards all logging calls to it.
 *
 * By default, it uses the `DefaultLogger`. This can be overridden at any time by calling `setLogger`.
 * This allows the SDK's logging to be redirected to a custom logging implementation.
 *
 * Example Usage in an Application's `onCreate`:
 * ```
 * if (BuildConfig.DEBUG) {
 *     Logger.setLogger(MyCustomDebugLogger())
 * }
 * ```
 */
object Logger : ILogger {
    private var logger: ILogger = DefaultLogger()

    /**
     * Overrides the default logger with a custom implementation.
     * @param newLogger The new `ILogger` instance to use for all subsequent log calls.
     */
    fun setLogger(newLogger: ILogger) {
        logger = newLogger
    }

    override fun d(tag: String, message: String) {
        logger.d(tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        logger.e(tag, message, throwable)
    }

    override fun w(tag: String, message: String) {
        logger.w(tag, message)
    }

    override fun i(tag: String, message: String) {
        logger.i(tag, message)
    }
}
