package com.github.adhamkhwaldeh.userBehaviorSDK.managers.base

import com.github.adhamkhwaldeh.userBehaviorSDK.exceptions.BaseUserBehaviorException
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs.IManagerConfigInterface
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.Logger
import java.util.concurrent.CopyOnWriteArrayList


abstract class BaseManager<TCall : ICallbackListener, TError : IErrorListener, TConfig : IManagerConfigInterface>(
    internal var config: TConfig,
    internal val logger: Logger,
) : IBaseManager<TCall, TError, TConfig> {

    internal val listeners: CopyOnWriteArrayList<TCall> = CopyOnWriteArrayList()
    //#region Listener
    /**
     * Add listener
     *
     * @param listener
     */
    override fun addListener(listener: TCall) {
        listeners.addIfAbsent(listener)
    }

    /**
     * Remove listener
     *
     * @param listener
     */
    override fun removeListener(listener: TCall) {
        listeners.remove(listener)
    }

    override fun clearListeners() {
        listeners.clear()
    }

    //#endregion
    internal val errorListeners: CopyOnWriteArrayList<TError> = CopyOnWriteArrayList()
    //#region Error Listener
    /**
     * Add error listener
     *
     * @param listener
     */
    override fun addErrorListener(listener: TError) {
        errorListeners.addIfAbsent(listener)
    }

    /**
     * Remove error listener
     *
     * @param listener
     */
    override fun removeErrorListener(listener: TError) {
        errorListeners.remove(listener)
    }

    /**
     * Remove all error listeners.
     */
    override fun clearErrorListeners() {
        errorListeners.clear()
    }

    override fun notifyErrorListeners(error: BaseUserBehaviorException) {
        for (listener in errorListeners) {
            listener.onError(error)
        }
    }
    //#endregion

    //#region Configuration

    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    override fun setEnabled(enabled: Boolean): IBaseConfigurableManager<TConfig> {
        this.config.isEnabled = enabled
        return this
    }

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    override fun setDebugMode(debugMode: Boolean): IBaseConfigurableManager<TConfig> {
        this.config.isDebugMode = debugMode
        return this
    }

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    override fun setLoggingEnabled(loggingEnabled: Boolean): IBaseConfigurableManager<TConfig> {
        config.isLoggingEnabled = loggingEnabled
        return this
    }

    /**
     * Update config
     *
     * @param config
     * @return
     */
    override fun updateConfig(config: TConfig): IBaseConfigurableManager<TConfig> {
        this.config = config
        return this
    }

    override fun updateDefaultConfig(config: IManagerConfigInterface): IBaseConfigurableManager<TConfig> {
        this.config.updateDefaultConfig(config)
        return this
    }

    override fun canOverride(): Boolean {
        return this.config.overridable
    }
    //#endregion

}