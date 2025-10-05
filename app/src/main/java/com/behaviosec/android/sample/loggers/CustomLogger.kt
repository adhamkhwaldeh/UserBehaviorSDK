package com.behaviosec.android.sample.loggers

import com.github.adhamkhwaldeh.userBehaviorSDK.config.BaseManagerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.ILogger

class CustomLogger : ILogger {
    override fun d(
        tag: String,
        message: String,
        config: BaseManagerConfig
    ) {
        println("DEBUG [$tag]: $message")

    }

    override fun e(
        tag: String,
        message: String,
        config: BaseManagerConfig,
        throwable: Throwable?
    ) {
        // Report to Crashlytics, Sentry, etc.
        println("ERROR [$tag]: $message")
        throwable?.printStackTrace()
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseManagerConfig
    ) {

    }

    override fun i(
        tag: String,
        message: String,
        config: BaseManagerConfig
    ) {

    }
}