package com.github.adhamkhwaldeh.userBehaviorSDK

import android.app.Activity
import android.content.Context
import android.view.View
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs.IManagerConfigInterface
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.AccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.TouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.IBaseManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerAccelerometerKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerActivityKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerTouchKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerViewKey
import com.github.adhamkhwaldeh.userBehaviorSDK.repositories.HelpersRepository
import java.util.WeakHashMap
import kotlin.collections.get

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
    private val behaviorManagers =
        WeakHashMap<ManagerKey, IBaseManager<out ICallbackListener, out IErrorListener, out IManagerConfigInterface>>()

    private val accelerometerManager by lazy {
        AccelerometerManager.create(appContext, HelpersRepository())
    }

    init {
        behaviorManagers[ManagerAccelerometerKey] = accelerometerManager
    }

    //#region general functions
    fun startTouchTracking(
        managerTouchKey: ManagerTouchKey,
        config: TouchConfig = TouchConfig()
    ) {
        if (behaviorManagers.containsKey(managerTouchKey)) return
        val manager = TouchManager.Builder()
            .forManagerKey(managerTouchKey)
            .withConfig(config).build()
        manager.start()
        behaviorManagers[managerTouchKey] = manager
    }
    //#endregion

    // --- TOUCH MANAGER FACADE ---

    /**
     * Starts tracking touch events for the scope of an entire Activity.
     * @param activity The Activity to track.
     * @param config Optional configuration for the touch tracker.
     */
    fun startTouchTracking(activity: Activity, config: TouchConfig = TouchConfig()) {
        startTouchTracking(ManagerActivityKey(activity), config)
    }

    /**
     * Starts tracking touch events for the scope of a specific View.
     * @param view The View to track.
     * @param config Optional configuration for the touch tracker.
     */
    fun startTouchTracking(view: View, config: TouchConfig = TouchConfig()) {
        startTouchTracking(ManagerViewKey(view), config)
    }

    /**
     * Stops tracking touch events for the given scope (Activity or View).
     * @param scope The Activity or View to stop tracking.
     */
    fun stopTouchTracking(scope: Any) {
        behaviorManagers.remove(scope)?.stop()
    }

    /**
     * Pauses touch tracking for the given scope (Activity or View).
     * @param scope The Activity or View to pause tracking.
     */
    fun pauseTouchTracking(scope: Any) {
        behaviorManagers[scope]?.pause()
    }

    /**
     * Resumes touch tracking for the given scope (Activity or View).
     * @param scope The Activity or View to resume tracking.
     */
    fun resumeTouchTracking(scope: Any) {
        behaviorManagers[scope]?.resume()
    }

    /**
     * Clears all touch listeners for the given scope.
     * @param scope The Activity or View to clear listeners from.
     */
    fun clearTouchListeners(scope: Any) {
        (behaviorManagers[scope] as? IBaseManager<TouchListener, *, TouchConfig>)?.clearListeners()
    }

    /**
     * Clears all touch error listeners for the given scope.
     * @param scope The Activity or View to clear error listeners from.
     */
    fun clearTouchErrorListeners(scope: Any) {
        (behaviorManagers[scope] as? IBaseManager<*, TouchErrorListener, TouchConfig>)?.clearListeners()
    }

    // --- SENSOR MANAGER FACADE ---

    /**
     * Starts tracking accelerometer events.
     * @param config Optional configuration for the accelerometer tracker.
     */
    fun startAccelerometerTracking(config: AccelerometerConfig = AccelerometerConfig()) {
        if (behaviorManagers.containsKey(ManagerAccelerometerKey)) return
        accelerometerManager.start()
        behaviorManagers[ManagerAccelerometerKey] = accelerometerManager
    }

    /**
     * Stops tracking accelerometer events.
     */
    fun stopAccelerometerTracking() {
        behaviorManagers.remove(ManagerAccelerometerKey)?.stop()
    }

    /**
     * Pauses accelerometer tracking.
     */
    fun pauseAccelerometerTracking() {
        (behaviorManagers[ManagerAccelerometerKey] as? IBaseManager<*, *, *>)?.pause()
    }

    /**
     * Resumes accelerometer tracking.
     */
    fun resumeAccelerometerTracking() {
        (behaviorManagers[ManagerAccelerometerKey] as? IBaseManager<*, *, *>)?.resume()
    }

    /**
     * Clears all accelerometer listeners.
     */
    fun clearAccelerometerListeners() {
        (behaviorManagers[ManagerAccelerometerKey] as? IBaseManager<AccelerometerListener, *, AccelerometerConfig>)?.clearListeners()
    }

    /**
     * Clears all accelerometer error listeners.
     */
    fun clearAccelerometerErrorListeners() {
        (behaviorManagers[ManagerAccelerometerKey] as? IBaseManager<*, AccelerometerErrorListener, AccelerometerConfig>)?.clearErrorListeners()
    }

    /**
     * Adds a listener for touch events for a given scope (Activity or View).
     * @param scope The scope to listen to.
     * @param listener The listener to add.
     */
    fun addTouchListener(scope: Any, listener: TouchListener) {
        (behaviorManagers[scope] as? IBaseManager<TouchListener, *, AccelerometerConfig>)?.addListener(
            listener
        )
    }

    /**
     * Removes a listener for touch events from a given scope.
     * @param scope The scope to remove the listener from.
     * @param listener The listener to remove.
     */
    fun removeTouchListener(scope: Any, listener: TouchListener) {
        (behaviorManagers[scope] as? IBaseManager<TouchListener, *, AccelerometerConfig>)?.removeListener(
            listener
        )
    }

    fun addTouchErrorListener(scope: Any, listener: TouchErrorListener) {
        (behaviorManagers[scope] as? IBaseManager<*, TouchErrorListener, AccelerometerConfig>)?.addErrorListener(
            listener
        )
    }

    fun removeTouchErrorListener(scope: Any, listener: TouchErrorListener) {
        (behaviorManagers[scope] as? IBaseManager<*, TouchErrorListener, AccelerometerConfig>)?.removeErrorListener(
            listener
        )
    }

    /**
     * Adds a listener for accelerometer events.
     * @param listener The listener to add.
     */
    fun addAccelerometerListener(listener: AccelerometerListener) {
        (behaviorManagers[ManagerAccelerometerKey] as? IBaseManager<AccelerometerListener, *, AccelerometerConfig>)?.addListener(
            listener
        )
    }

    /**
     * Removes a listener for accelerometer events.
     * @param listener The listener to remove.
     */
    fun removeAccelerometerListener(listener: AccelerometerListener) {
        (behaviorManagers[ManagerAccelerometerKey] as? IBaseManager<AccelerometerListener, *, AccelerometerConfig>)?.removeListener(
            listener
        )
    }

    fun addAccelerometerErrorListener(listener: AccelerometerErrorListener) {
        (behaviorManagers[ManagerAccelerometerKey] as? IBaseManager<*, AccelerometerErrorListener, AccelerometerConfig>)?.addErrorListener(
            listener
        )
    }

    fun removeAccelerometerErrorListener(listener: AccelerometerErrorListener) {
        (behaviorManagers[ManagerAccelerometerKey] as? IBaseManager<*, AccelerometerErrorListener, AccelerometerConfig>)?.removeErrorListener(
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