package com.github.adhamkhwaldeh.userBehaviorSDK.options

import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

class UserBehaviorSDKOptions private constructor(
    isEnabled: Boolean,
    isDebugMode: Boolean,
    isLoggingEnabled: Boolean,
    overridable: Boolean,
    logLevel: LogLevel,
) : BaseSDKOptions(isEnabled, isDebugMode, isLoggingEnabled, overridable, logLevel) {

    /**
     * The Builder for creating UserBehaviorSDKConfig instances.
     */
    class Builder : BaseBuilder<Builder, UserBehaviorSDKOptions>() {
        /**
         * Creates the final UserBehaviorSDKConfig object.
         */
        override fun build(): UserBehaviorSDKOptions {
            return UserBehaviorSDKOptions(
                isEnabled = isEnabled,
                isDebugMode = isDebugMode,
                isLoggingEnabled = isLoggingEnabled,
                logLevel = logLevel,
                overridable = overridable,
            )
        }
    }
}