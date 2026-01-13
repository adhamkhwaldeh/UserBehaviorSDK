package com.behaviosec.android.sample.loggers

import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions
import com.github.adhamkhwaldeh.commonsdk.logging.Logger

class CustomLogger : Logger {
    override fun d(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {
        println("DEBUG [$tag]: $message")

    }

    override fun e(
        tag: String,
        message: String,
        config: BaseSDKOptions,
        throwable: Throwable?
    ) {
        // Report to Crashlytics, Sentry, etc.
        println("ERROR [$tag]: $message")
        throwable?.printStackTrace()
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {

    }

    override fun i(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {

    }
}