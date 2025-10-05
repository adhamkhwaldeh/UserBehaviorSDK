package com.github.adhamkhwaldeh.userBehaviorSDK

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.SensorConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.SensorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs.IManagerConfigInterface
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.SensorErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.ILogger
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.Logger
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.AccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.TouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.IBaseManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.sensors.ISensorsManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.sensors.SensorsManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerAccelerometerKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerActivityKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerApplicationKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerSensorKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerTouchKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerViewKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorAccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.repositories.HelpersRepository
import java.util.WeakHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * User Behavior Core SDK
 *
 * This is the main entry point for the UserBehavior SDK. It provides a facade to access
 * various data collection managers. Use the singleton `getInstance(context)` to get started.
 *
 * Example:
 * val sdk = UserBehaviorCoreSDK.getInstance(applicationContext)
 * val touchManager = sdk.fetchOrCreateActivityTouchManager(myActivity)
 * touchManager.start()

 * Example:
 * val sdk = UserBehaviorCoreSDK.getInstance(applicationContext)
 * sdk.startAll()
 * sdk.addGlobalErrorListener(myErrorListener)
 */

class UserBehaviorCoreSDK private constructor(context: Context) {
    /**
     * Builder for creating and configuring a `UserBehaviorCoreSDK` instance.
     * @param context The application context.
     */
    class Builder(private val context: Context) {
        private val customLoggers = mutableListOf<ILogger>()

        /**
         * Adds a custom logger implementation. Multiple loggers can be added.
         * If at least one custom logger is provided, the default logcat logger will be cleared.
         *
         * @param logger The custom logger to add.
         * @return The Builder instance for chaining.
         */
        fun addLogger(logger: ILogger): Builder {
            customLoggers.add(logger)
            return this
        }

        /**
         * Builds and returns a configured instance of the UserBehaviorCoreSDK.
         *
         * @return A new instance of UserBehaviorCoreSDK.
         */
        fun build(): UserBehaviorCoreSDK {
            val sdk = UserBehaviorCoreSDK(context)
            // Configure the global logger based on the builder settings.
            if (customLoggers.isNotEmpty()) {
                sdk.logger.clearLoggers()
                customLoggers.forEach { sdk.logger.addLogger(it) }
            }
            return sdk
        }
    }

    private val appContext: Context = context.applicationContext

    // Using a WeakHashMap allows garbage collection of the Activity/View keys when they are destroyed,
    // preventing memory leaks.
    private val behaviorManagers =
        WeakHashMap<ManagerKey, IBaseManager<out ICallbackListener, out IErrorListener, out IManagerConfigInterface>>()

    private val globalViewsListeners = CopyOnWriteArrayList<TouchListener>()
    private val globalErrorListeners = CopyOnWriteArrayList<IErrorListener>()

    private val globalSensorsListeners = CopyOnWriteArrayList<SensorListener>()

    private val logger by lazy {
        Logger()
    }

    private val accelerometerManager by lazy {
        AccelerometerManager.create(appContext, HelpersRepository(), logger = logger)
    }

    init {
        behaviorManagers[ManagerAccelerometerKey] = accelerometerManager

        // Add a forwarding listener to pass errors to the global listeners
        accelerometerManager.addErrorListener(object : AccelerometerErrorListener {
            override fun onError(error: ManagerErrorModel) {
                globalErrorListeners.forEach { it.onError(error) }
            }
        })
    }

    //#region SDK-level Actions
    /**
     * Starts all currently active managers.
     */
    fun startAll() {
        behaviorManagers.values.forEach {
            if (!it.isStarted())
                it.start()
        }
    }

    /**
     * Stops all currently active managers.
     */
    fun stopAll() {
        behaviorManagers.values.forEach {
            if (it.isStarted())
                it.stop()
        }
    }

    /**
     * Pauses all currently active managers.
     */
    fun pauseAll() {
        behaviorManagers.values.forEach {
            if (it.isStarted())
                it.pause()
        }
    }

    /**
     * Resumes all currently active managers.
     */
    fun resumeAll() {
        behaviorManagers.values.forEach {
            if (!it.isStarted())
                it.resume()
        }
    }

    /**
     * Adds a listener that will receive errors from all managers in the SDK.
     * @param listener The listener to add.
     */
    fun addGlobalErrorListener(listener: IErrorListener) {
        globalErrorListeners.addIfAbsent(listener)
    }

    /**
     * Removes a global error listener.
     * @param listener The listener to remove.
     */
    fun removeGlobalErrorListener(listener: IErrorListener) {
        globalErrorListeners.remove(listener)
    }

    /**
     * Adds a listener that will receive views touch from all managers in the SDK.
     * @param listener The listener to add.
     */
    fun addGlobalViewsListener(listener: TouchListener) {
        globalViewsListeners.addIfAbsent(listener)
    }

    /**
     * Removes a global views listener.
     * @param listener The listener to remove.
     */
    fun removeGlobalViewListener(listener: TouchListener) {
        globalViewsListeners.remove(listener)
    }

    /**
     * Adds a listener that will receive sensors update from all managers in the SDK.
     * @param listener The listener to add.
     */
    fun addGlobalSensorListener(listener: SensorListener) {
        globalSensorsListeners.addIfAbsent(listener)
    }

    /**
     * Removes a global sensors listener.
     * @param listener The listener to remove.
     */
    fun removeGlobalSensorListener(listener: SensorListener) {
        globalSensorsListeners.remove(listener)
    }
    //#endregion

    //#region Touch Managers
    private fun fetchOrCreateTouchManager(
        managerTouchKey: ManagerTouchKey,
        config: TouchConfig? = null
    ): ITouchManager {
        val existingManager = behaviorManagers[managerTouchKey]
        if (existingManager != null) {
            @Suppress("UNCHECKED_CAST")
            // This cast is safe because the public-facing methods ensure we only fetch a manager
            // created by this method, which has a known type.
            val readManager = existingManager as ITouchManager
            if (config != null) {
                readManager.updateConfig(config)
            }
            return readManager
        }

        val manager = TouchManager.Builder(logger)
            .forManagerKey(managerTouchKey).apply {
                if (config != null) {
                    withConfig(config)
                }
            }
            .build()
        // Add a forwarding listener to pass errors to the global listeners
        manager.addErrorListener(object : TouchErrorListener {
            override fun onError(error: ManagerErrorModel) {
                globalErrorListeners.forEach { it.onError(error) }
            }
        })

        behaviorManagers[managerTouchKey] = manager
        return manager
    }

    @JvmOverloads
    fun fetchOrCreateActivityTouchManager(
        activity: Activity,
        config: TouchConfig? = null
    ): ITouchManager {
        return fetchOrCreateTouchManager(
            ManagerActivityKey(activity),
            config
        )
    }

    @JvmOverloads
    fun fetchOrCreateApplicationTouchManager(
        application: Application,
        config: TouchConfig? = null
    ): ITouchManager {
        return fetchOrCreateTouchManager(
            ManagerApplicationKey(application),
            config
        )
    }

    @JvmOverloads
    fun fetchOrCreateViewTouchManager(
        view: View,
        config: TouchConfig? = null
    ): ITouchManager {
        val manager = fetchOrCreateTouchManager(
            ManagerViewKey(view),
            config
        )

        // Add a forwarding listener to pass actions to the global listeners
        manager.addListener(object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                globalViewsListeners.forEach { it.dispatchTouchEvent(event) }
                return super.dispatchTouchEvent(event)
            }
        })

        return manager
    }

    //#endregion

    //#region Accelerometer Manager
    @JvmOverloads
    fun getAccelerometerManager(config: AccelerometerConfig? = null): IAccelerometerManager {
        if (config != null) {
            accelerometerManager.config = config
        }
        // Ensure the manager is in the map if it was somehow removed.
        if (!behaviorManagers.containsKey(ManagerAccelerometerKey)) {
            behaviorManagers[ManagerAccelerometerKey] = accelerometerManager
        }
        return accelerometerManager
    }
    //#endregion

    //#region Sensors Manager
    fun fetchOrCreateSensorManager(
        managerSensorKey: ManagerSensorKey,
        config: SensorConfig? = null
    ): ISensorsManager {
        val existingManager = behaviorManagers[managerSensorKey]
        if (existingManager != null) {
            @Suppress("UNCHECKED_CAST")
            // This cast is safe because the public-facing methods ensure we only fetch a manager
            // created by this method, which has a known type.
            val readManager = existingManager as ISensorsManager
            if (config != null) {
                readManager.updateConfig(config)
            }
            return readManager
        }

        val manager =
            SensorsManager.create(
                context = appContext,
                helpersRepository = HelpersRepository(),
                sensorType = managerSensorKey.sensorType,
                logger = logger
            )

        // Add a forwarding listener to pass errors to the global listeners
        manager.addErrorListener(object : SensorErrorListener {
            override fun onError(error: ManagerErrorModel) {
                globalErrorListeners.forEach { it.onError(error) }
            }
        })

        // Add a forwarding listener to pass actions to the global listeners
        manager.addListener(object : SensorListener {

            override fun onSensorChanged(model: SensorEventModel) {
                globalSensorsListeners.forEach { it.onSensorChanged(model) }
                return super.onSensorChanged(model)
            }

            override fun onAccuracyChanged(model: SensorAccuracyChangedModel) {
                globalSensorsListeners.forEach { it.onAccuracyChanged(model) }
                return super.onAccuracyChanged(model)
            }
        })

        behaviorManagers[managerSensorKey] = manager
        return manager
    }
    //#endregion


//    companion object {
//        @Volatile
//        private var INSTANCE: UserBehaviorCoreSDK? = null
//
//        /**
//         * Gets the singleton instance of the UserBehaviorCoreSDK.
//         * @param context The application context.
//         * @return The singleton instance.
//         */
//        @JvmStatic
//        fun getInstance(context: Context): UserBehaviorCoreSDK {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: UserBehaviorCoreSDK(context).also { INSTANCE = it }
//            }
//        }
//    }

}