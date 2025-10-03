package com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.github.adhamkhwaldeh.userBehaviorSDK.R
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.Logger
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.BaseManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel

/**
 * App touch manager
 *
 * @property application
 * @constructor Create empty App touch manager
 */
internal class AppTouchManager private constructor(
    private val application: Application,
    config: TouchConfig = TouchConfig()
) : BaseManager<TouchListener, TouchErrorListener, TouchConfig>(config) {

    companion object {
        @JvmSynthetic
        internal fun create(
            application: Application,
            config: TouchConfig = TouchConfig(),
        ): AppTouchManager = AppTouchManager(
            application = application,
            config = config
        )
    }

    private val activityManagers = mutableMapOf<Activity, ActivityTouchManager>()

    //    Using activityManagersLock provides thread safety when accessing or modifying the activityManagers map.
//    This ensures that concurrent operations (such as adding or removing activities from different threads)
//    do not cause race conditions, inconsistent state, or crashes.
//    It is especially important in Android, where lifecycle callbacks may be invoked from different threads.
    private val activityManagersLock = Any()
    private val lifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            try {
                val activityTouchManager = ActivityTouchManager.create(activity)
                // Add all global listeners to the new manager
                listeners.forEach { activityTouchManager.addListener(it) }
                synchronized(activityManagersLock) {
                    activityManagers[activity] = activityTouchManager
                }
                Logger.i(
                    application.getString(R.string.app_touch_manager),
                    application.getString(
                        R.string.on_activity_created,
                        activity.javaClass.simpleName
                    )
                )
            } catch (e: Exception) {
                errorListeners.forEach { listener ->
                    listener.onError(
                        ManagerErrorModel(
                            e,
                            application.getString(
                                R.string.failed_to_create_activity_touch_manager,
                                e.message ?: ""
                            )
                        )
                    )
                }
            }
        }

        override fun onActivityStarted(activity: Activity) {}

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {
            try {
                synchronized(activityManagersLock) {
                    activityManagers.remove(activity)
                }
                Logger.i(
                    application.getString(R.string.app_touch_manager),
                    application.getString(
                        R.string.on_activity_destroyed,
                        activity.javaClass.simpleName
                    )
                )
            } catch (e: Exception) {
                errorListeners.forEach { listener ->
                    listener.onError(
                        ManagerErrorModel(
                            e,
                            application.getString(
                                R.string.failed_to_remove_activity_touch_manager,
                                e.message ?: ""
                            ),
                        )
                    )
                }
            }
        }
    }

    //#region Base Manager actions
    override fun start() {
        application.registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }

    override fun stop() {
        application.unregisterActivityLifecycleCallbacks(lifecycleCallbacks)
    }

    override fun pause() {
        application.unregisterActivityLifecycleCallbacks(lifecycleCallbacks)
    }

    override fun resume() {
        application.registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }
    //#endregion

    //#region IBaseDataManager implementation
    /**
     * Add a global listener to receive touch events from all activities.
     */
    override fun addListener(listener: TouchListener) {
        super.addListener(listener)
        synchronized(activityManagersLock) {
            activityManagers.values.forEach { it.addListener(listener) }
        }
    }

    /**
     * Remove a global listener from all activities.
     */
    override fun removeListener(listener: TouchListener) {
        super.removeListener(listener)
        synchronized(activityManagersLock) {
            activityManagers.values.forEach { it.removeListener(listener) }
        }
    }

    /**
     * Remove all global listeners from all activities.
     */
    override fun clearListeners() {
        super.clearListeners()
        synchronized(activityManagersLock) {
            activityManagers.values.forEach { it.clearListeners() }
        }
    }
    //#endregion

}
