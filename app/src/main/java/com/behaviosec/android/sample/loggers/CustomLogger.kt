package com.behaviosec.android.sample.loggers

import com.github.adhamkhwaldeh.commonsdk.config.BaseBehaviorConfig
import com.github.adhamkhwaldeh.commonsdk.config.UserBehaviorSDKConfig
import com.github.adhamkhwaldeh.commonsdk.logging.ILogger

class CustomLogger : ILogger {
    override fun d(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {
        println("DEBUG [$tag]: $message")

    }

    override fun e(
        tag: String,
        message: String,
        config: BaseBehaviorConfig,
        throwable: Throwable?
    ) {
        // Report to Crashlytics, Sentry, etc.
        println("ERROR [$tag]: $message")
        throwable?.printStackTrace()
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {

    }

    override fun i(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {

    }
}