package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs

interface IManagerConfigInterface : IServiceStatusInterface, IBuildTypeInterface,
    ILogConfigInterface {


    fun updateDefaultConfig(configure: IManagerConfigInterface) {
        isLoggingEnabled = configure.isLoggingEnabled
        logLevel = configure.logLevel
        isEnabled = configure.isEnabled
        isDebugMode = configure.isDebugMode

//        overridable = configure.overridable
    }


}