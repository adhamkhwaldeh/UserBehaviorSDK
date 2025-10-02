package com.behaviosec.android.userBehaviorSDK

import android.app.Activity
import android.content.Context
import android.view.View
import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.ICallbackListener
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.TouchListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.IErrorListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.behaviosec.android.userBehaviorSDK.managers.AccelerometerManager
import com.behaviosec.android.userBehaviorSDK.managers.TouchManager
import com.behaviosec.android.userBehaviorSDK.managers.base.IBaseManager
import com.behaviosec.android.userBehaviorSDK.models.ManageAccelerometerKey
import com.behaviosec.android.userBehaviorSDK.models.ManageActivityKey
import com.behaviosec.android.userBehaviorSDK.models.ManageViewKey
import com.behaviosec.android.userBehaviorSDK.models.ManagerKey
import com.behaviosec.android.userBehaviorSDK.repositories.HelpersRepository
import java.util.WeakHashMap

/**
 * User Behavior Core SDK
 *
 * This is the main entry point for the UserBehavior SDK. It provides a facade to access
 * various data collection managers. Use the singleton `getInstance(context)` to get started.
 *
 * Example:
 * val sdk = UserBehaviorCoreSDK.getInstance(applicationContext)
 * sdk.startTouchTracking(myActivity)
 * sdk.addTouchListener(myActivity, myTouchListener)
 * sdk.startAccelerometerTracking()
 */
class UserBehaviorCoreSDK private constructor(context: Context) {

    private val appContext: Context = context.applicationContext

    // Using a WeakHashMap allows garbage collection of the Activity/View keys when they are destroyed,
    // preventing memory leaks.
    private val activeManagers =
        WeakHashMap<ManagerKey, IBaseManager<out ICallbackListener, out IErrorListener>>()

    // --- TOUCH MANAGER FACADE ---

    /**
     * Starts tracking touch events for the scope of an entire Activity.
     * @param activity The Activity to track.
     * @param config Optional configuration for the touch tracker.
     */
    fun startTouchTracking(activity: Activity, config: TouchTrackerConfig = TouchTrackerConfig()) {
        val key = ManageActivityKey(activity)
        if (activeManagers.containsKey(key)) return
        val manager = TouchManager.Builder().forActivity(activity).withConfig(config).build()
        manager.start()
        activeManagers[key] = manager
    }

    /**
     * Starts tracking touch events for the scope of a specific View.
     * @param view The View to track.
     * @param config Optional configuration for the touch tracker.
     */
    fun startTouchTracking(view: View, config: TouchTrackerConfig = TouchTrackerConfig()) {
        val key = ManageViewKey(view)
        if (activeManagers.containsKey(key)) return
        val manager = TouchManager.Builder().forView(view).withConfig(config).build()
        manager.start()
        activeManagers[key] = manager
    }

    /**
     * Stops tracking touch events for the given scope (Activity or View).
     * @param scope The Activity or View to stop tracking.
     */
    fun stopTouchTracking(scope: Any) {
        activeManagers.remove(scope)?.stop()
    }

    /**
     * Pauses touch tracking for the given scope (Activity or View).
     * @param scope The Activity or View to pause tracking.
     */
    fun pauseTouchTracking(scope: Any) {
        activeManagers[scope]?.pause()
    }

    /**
     * Resumes touch tracking for the given scope (Activity or View).
     * @param scope The Activity or View to resume tracking.
     */
    fun resumeTouchTracking(scope: Any) {
        activeManagers[scope]?.resume()
    }

    /**
     * Clears all touch listeners for the given scope.
     * @param scope The Activity or View to clear listeners from.
     */
    fun clearTouchListeners(scope: Any) {
        (activeManagers[scope] as? IBaseManager<TouchListener, *>)?.listeners?.clear()
    }

    /**
     * Clears all touch error listeners for the given scope.
     * @param scope The Activity or View to clear error listeners from.
     */
    fun clearTouchErrorListeners(scope: Any) {
        (activeManagers[scope] as? IBaseManager<*, TouchErrorListener>)?.errorListeners?.clear()
    }

    // --- SENSOR MANAGER FACADE ---

    /**
     * Starts tracking accelerometer events.
     * @param config Optional configuration for the accelerometer tracker.
     */
    fun startAccelerometerTracking(config: TouchTrackerConfig = TouchTrackerConfig()) {
        if (activeManagers.containsKey(ManageAccelerometerKey)) return
        val manager = AccelerometerManager(appContext, HelpersRepository(), config)
        manager.start()
        activeManagers[ManageAccelerometerKey] = manager
    }

    /**
     * Stops tracking accelerometer events.
     */
    fun stopAccelerometerTracking() {
        activeManagers.remove(ManageAccelerometerKey)?.stop()
    }

    /**
     * Pauses accelerometer tracking.
     */
    fun pauseAccelerometerTracking() {
        (activeManagers[ManageAccelerometerKey] as? IBaseManager<*, *>)?.pause()
    }

    /**
     * Resumes accelerometer tracking.
     */
    fun resumeAccelerometerTracking() {
        (activeManagers[ManageAccelerometerKey] as? IBaseManager<*, *>)?.resume()
    }

    /**
     * Clears all accelerometer listeners.
     */
    fun clearAccelerometerListeners() {
        (activeManagers[ManageAccelerometerKey] as? IBaseManager<AccelerometerListener, *>)?.listeners?.clear()
    }

    /**
     * Clears all accelerometer error listeners.
     */
    fun clearAccelerometerErrorListeners() {
        (activeManagers[ManageAccelerometerKey] as? IBaseManager<*, AccelerometerErrorListener>)?.errorListeners?.clear()
    }

    /**
     * Adds a listener for touch events for a given scope (Activity or View).
     * @param scope The scope to listen to.
     * @param listener The listener to add.
     */
    fun addTouchListener(scope: Any, listener: TouchListener) {
        (activeManagers[scope] as? IBaseManager<TouchListener, *>)?.addListener(listener)
    }

    /**
     * Removes a listener for touch events from a given scope.
     * @param scope The scope to remove the listener from.
     * @param listener The listener to remove.
     */
    fun removeTouchListener(scope: Any, listener: TouchListener) {
        (activeManagers[scope] as? IBaseManager<TouchListener, *>)?.removeListener(listener)
    }

    fun addTouchErrorListener(scope: Any, listener: TouchErrorListener) {
        (activeManagers[scope] as? IBaseManager<*, TouchErrorListener>)?.addErrorListener(listener)
    }

    fun removeTouchErrorListener(scope: Any, listener: TouchErrorListener) {
        (activeManagers[scope] as? IBaseManager<*, TouchErrorListener>)?.removeErrorListener(
            listener
        )
    }

    /**
     * Adds a listener for accelerometer events.
     * @param listener The listener to add.
     */
    fun addAccelerometerListener(listener: AccelerometerListener) {
        (activeManagers[ManageAccelerometerKey] as? IBaseManager<AccelerometerListener, *>)?.addListener(
            listener
        )
    }

    /**
     * Removes a listener for accelerometer events.
     * @param listener The listener to remove.
     */
    fun removeAccelerometerListener(listener: AccelerometerListener) {
        (activeManagers[ManageAccelerometerKey] as? IBaseManager<AccelerometerListener, *>)?.removeListener(
            listener
        )
    }

    fun addAccelerometerErrorListener(listener: AccelerometerErrorListener) {
        (activeManagers[ManageAccelerometerKey] as? IBaseManager<*, AccelerometerErrorListener>)?.addErrorListener(
            listener
        )
    }

    fun removeAccelerometerErrorListener(listener: AccelerometerErrorListener) {
        (activeManagers[ManageAccelerometerKey] as? IBaseManager<*, AccelerometerErrorListener>)?.removeErrorListener(
            listener
        )
    }

    companion object {
        @Volatile
        private var INSTANCE: UserBehaviorCoreSDK? = null

        /**
         * Gets the singleton instance of the UserBehaviorCoreSDK.
         * @param context The application context.
         * @return The singleton instance.
         */
        fun getInstance(context: Context): UserBehaviorCoreSDK {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserBehaviorCoreSDK(context).also { INSTANCE = it }
            }
        }
    }

}
