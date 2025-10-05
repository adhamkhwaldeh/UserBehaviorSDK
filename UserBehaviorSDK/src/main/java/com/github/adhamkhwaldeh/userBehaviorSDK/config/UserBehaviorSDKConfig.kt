package com.github.adhamkhwaldeh.userBehaviorSDK.config

import com.github.adhamkhwaldeh.userBehaviorSDK.logging.LogLevel

class UserBehaviorSDKConfig private constructor(
    isEnabled: Boolean,
    isDebugMode: Boolean,
    isLoggingEnabled: Boolean,
    overridable: Boolean,
    logLevel: LogLevel,
) : BaseBehaviorConfig(isEnabled, isDebugMode, isLoggingEnabled, overridable, logLevel) {

    /**
     * The Builder for creating UserBehaviorSDKConfig instances.
     */
    class Builder : BaseBuilder<Builder, UserBehaviorSDKConfig>() {
        /**
         * Creates the final UserBehaviorSDKConfig object.
         */
        override fun build(): UserBehaviorSDKConfig {
            return UserBehaviorSDKConfig(
                isEnabled = isEnabled,
                isDebugMode = isDebugMode,
                isLoggingEnabled = isLoggingEnabled,
                logLevel = logLevel,
                overridable = overridable,
            )
        }
    }
}