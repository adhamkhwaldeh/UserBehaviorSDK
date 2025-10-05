package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs

import com.github.adhamkhwaldeh.userBehaviorSDK.logging.LogLevel

interface ILogConfigInterface {
    var isLoggingEnabled: Boolean

    var overridable: Boolean

    var logLevel: LogLevel
}