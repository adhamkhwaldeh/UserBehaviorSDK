package com.behaviosec.android.accelerometerTouchTrackerSdk.managers

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.ActivityTouchListener

class AppTouchManager(application: Application) : Application.ActivityLifecycleCallbacks {

    private val activityManagers = mutableMapOf<Activity, ActivityTouchManager>()
    private var globalTouchListener: ActivityTouchListener? = null

    init {
        application.registerActivityLifecycleCallbacks(this)
        Log.d("AppTouchManager", "AppTouchManager initialized")
    }

    /**
     * Set a global listener to receive touch events from all activities.
     */
    fun setGlobalTouchListener(listener: ActivityTouchListener) {
        globalTouchListener = listener
        // Set the listener for existing managers
        activityManagers.values.forEach { it.setListener(listener) }
    }

    /**
     * Remove the global listener from all activities.
     */
    fun removeGlobalTouchListener() {
        globalTouchListener = null
        activityManagers.values.forEach { it.removeListener() }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val activityTouchManager = ActivityTouchManager(activity)
        globalTouchListener?.let { activityTouchManager.setListener(it) }
        activityManagers[activity] = activityTouchManager
        Log.d("AppTouchManager", "onActivityCreated: ${activity.javaClass.simpleName}")
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
        activityManagers.remove(activity)
        Log.d("AppTouchManager", "onActivityDestroyed: ${activity.javaClass.simpleName}")
    }
}
