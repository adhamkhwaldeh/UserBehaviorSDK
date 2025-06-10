package com.behaviosec.android.accelerometerTouchTrackerSdk.managers

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.behaviosec.android.accelerometerTouchTrackerSdk.R
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.ActivityTouchListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AppTouchErrorListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.logging.Logger
import com.behaviosec.android.accelerometerTouchTrackerSdk.models.ManagerErrorModel

/**
 * App touch manager
 *
 * @property application
 * @constructor Create empty App touch manager
 */
class AppTouchManager(
    private val application: Application
) : Application.ActivityLifecycleCallbacks {

    private val activityManagers = mutableMapOf<Activity, ActivityTouchManager>()

    //    Using activityManagersLock provides thread safety when accessing or modifying the activityManagers map.
//    This ensures that concurrent operations (such as adding or removing activities from different threads)
//    do not cause race conditions, inconsistent state, or crashes.
//    It is especially important in Android, where lifecycle callbacks may be invoked from different threads.
    private val activityManagersLock = Any()
    private var globalTouchListener: ActivityTouchListener? = null
    private var errorListener: AppTouchErrorListener? = null

    init {
        application.registerActivityLifecycleCallbacks(this)
        Logger.i(
            application.getString(R.string.app_touch_manager),
            application.getString(R.string.app_touch_manager_initialized)
        )
    }

    /**
     * Set a global listener to receive touch events from all activities.
     */
    fun setGlobalTouchListener(listener: ActivityTouchListener) {
        globalTouchListener = listener
        synchronized(activityManagersLock) {
            activityManagers.values.forEach { it.setListener(listener) }
        }
    }

    /**
     * Remove the global listener from all activities.
     */
    fun removeGlobalTouchListener() {
        globalTouchListener = null
        synchronized(activityManagersLock) {
            activityManagers.values.forEach { it.removeListener() }
        }
    }

    /**
     * Set an error listener to handle errors.
     */
    fun setErrorListener(listener: AppTouchErrorListener) {
        this.errorListener = listener
    }

    /**
     * Remove the error listener.
     */
    fun removeErrorListener() {
        this.errorListener = null
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        try {
            val activityTouchManager = ActivityTouchManager(activity)
            globalTouchListener?.let { activityTouchManager.setListener(it) }
            synchronized(activityManagersLock) {
                activityManagers[activity] = activityTouchManager
            }
            Logger.i(
                application.getString(R.string.app_touch_manager),
                application.getString(R.string.on_activity_created, activity.javaClass.simpleName)
            )
        } catch (e: Exception) {
            errorListener?.onAppTouchError(
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

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {

    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        try {
            synchronized(activityManagersLock) {
                activityManagers.remove(activity)
            }
            Logger.i(
                application.getString(R.string.app_touch_manager),
                application.getString(R.string.on_activity_destroyed, activity.javaClass.simpleName)
            )
        } catch (e: Exception) {
            errorListener?.onAppTouchError(
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
