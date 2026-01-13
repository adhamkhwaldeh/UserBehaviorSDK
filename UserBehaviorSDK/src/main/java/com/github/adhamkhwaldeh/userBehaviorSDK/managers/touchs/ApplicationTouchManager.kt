package com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.managers.BaseManager
import com.github.adhamkhwaldeh.commonsdk.managers.BaseManagerImpl
import com.github.adhamkhwaldeh.userBehaviorSDK.R
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.exceptions.FailToCreateActivityManagerException
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener


/**
 * App touch manager
 *
 * @property application
 * @constructor Create empty App touch manager
 */
internal class ApplicationTouchManager private constructor(
    private val application: Application,
    logger: Logger,
    config: TouchConfig,
) : BaseManagerImpl<TouchListener, TouchErrorListener, TouchConfig>(config, logger), ITouchManager {

    companion object {
        @JvmSynthetic
        internal fun create(
            application: Application,
            logger: Logger,
            config: TouchConfig,
        ): ApplicationTouchManager = ApplicationTouchManager(
            application = application,
            logger = logger,
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
                val activityTouchManager = ActivityTouchManager.create(activity, logger, config)
                // Add all global listeners to the new manager
                notifyListeners { activityTouchManager.addListener(it) }
//                delegatedListeners.forEach { activityTouchManager.addListener(it) }
                synchronized(activityManagersLock) {
                    activityManagers[activity] = activityTouchManager
                }
                logger.i(
                    application.getString(R.string.app_touch_manager),
                    application.getString(
                        R.string.on_activity_created,
                        activity.javaClass.simpleName
                    ),
                    config,
                )
            } catch (e: Exception) {
                notifyErrorListeners(
                    FailToCreateActivityManagerException(
                        message = application.getString(
                            R.string.failed_to_create_activity_touch_manager,
                            e.message ?: ""
                        ),
                        cause = e,
                    )
                )
//                delegatedErrorListeners.forEach { listener ->
//                    listener.onError(
//                        FailToCreateActivityManagerException(
//                            message = application.getString(
//                                R.string.failed_to_create_activity_touch_manager,
//                                e.message ?: ""
//                            ),
//                            cause = e,
//                        )
//                    )
//                }
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
                logger.i(
                    application.getString(R.string.app_touch_manager),
                    application.getString(
                        R.string.on_activity_destroyed,
                        activity.javaClass.simpleName
                    ),
                    config
                )
            } catch (e: Exception) {
                notifyErrorListeners(
                    FailToCreateActivityManagerException(
                        message = application.getString(
                            R.string.failed_to_create_activity_touch_manager,
                            e.message ?: ""
                        ),
                        cause = e,
                    )
                )
//                delegatedErrorListeners.forEach { listener ->
//                    listener.onError(
//                        FailToCreateActivityManagerException(
//                            message = application.getString(
//                                R.string.failed_to_create_activity_touch_manager,
//                                e.message ?: ""
//                            ),
//                            cause = e,
//                        )
//                    )
//                }
            }
        }
    }

    //#region Base Manager actions
    var isManagerStarted = false

    override fun isStarted(): Boolean {
        return isManagerStarted
    }

    override fun start() {
        application.registerActivityLifecycleCallbacks(lifecycleCallbacks)
        isManagerStarted = true
    }

    override fun stop() {
        application.unregisterActivityLifecycleCallbacks(lifecycleCallbacks)
        isManagerStarted = false
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
