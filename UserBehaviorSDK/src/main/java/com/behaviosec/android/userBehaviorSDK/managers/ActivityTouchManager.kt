package com.behaviosec.android.userBehaviorSDK.managers

import android.app.Activity
import android.view.MotionEvent
import android.view.Window.Callback
import com.behaviosec.android.userBehaviorSDK.helpers.DateHelpers
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.ActivityTouchListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.ActivityTouchErrorListener
import com.behaviosec.android.userBehaviorSDK.R
import com.behaviosec.android.userBehaviorSDK.logging.Logger
import com.behaviosec.android.userBehaviorSDK.models.MotionEventModel
import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Activity touch manager
 *
 * @constructor
 *
 * @param activity
 * @param config
 */
class ActivityTouchManager(
    private val activity: Activity,
    config: TouchTrackerConfig = TouchTrackerConfig()
) : BaseManager<ActivityTouchListener,ActivityTouchErrorListener>(config){

    private val callback = object : Callback {
        override fun dispatchTouchEvent(event: MotionEvent): Boolean {
            if (isLoggingEnabled && config.isDebugMode) {
                Logger.d(
                    activity.getString(R.string.global_touch_tracker),
                    activity.getString(
                        R.string.touch_event_action_xy,
                        event.action,
                        event.x,
                        event.y
                    )
                )
            }
            // If the manager is disabled, just delegate the event without logging
            if (isTrackingEnabled && config.isEnabled && listeners.isNotEmpty()) {
                val model = MotionEventModel(event, DateHelpers.getCurrentDate())
                var continueBase = true
                for (listener in listeners) {
                    try {
                        continueBase = continueBase && listener.dispatchTouchEvent(model)
                    } catch (e: Exception) {
                        notifyErrorListeners(e)
                    }
                }
                return if (continueBase) {
                    originalCallback.dispatchTouchEvent(event)
                } else {
                    false
                }
            }
            return originalCallback.dispatchTouchEvent(event)
        }

        // Boilerplate: delegate other methods
        override fun dispatchKeyEvent(event: android.view.KeyEvent) =
            originalCallback.dispatchKeyEvent(event)

        override fun dispatchKeyShortcutEvent(event: android.view.KeyEvent) =
            originalCallback.dispatchKeyShortcutEvent(event)

        override fun dispatchTrackballEvent(event: MotionEvent) =
            originalCallback.dispatchTrackballEvent(event)

        override fun dispatchGenericMotionEvent(event: MotionEvent) =
            originalCallback.dispatchGenericMotionEvent(event)

        override fun dispatchPopulateAccessibilityEvent(event: android.view.accessibility.AccessibilityEvent) =
            originalCallback.dispatchPopulateAccessibilityEvent(event)

        override fun onCreatePanelView(featureId: Int) =
            originalCallback.onCreatePanelView(featureId)

        override fun onCreatePanelMenu(featureId: Int, menu: android.view.Menu) =
            originalCallback.onCreatePanelMenu(featureId, menu)

        override fun onPreparePanel(
            featureId: Int,
            view: android.view.View?,
            menu: android.view.Menu
        ) =
            originalCallback.onPreparePanel(featureId, view, menu)

        override fun onMenuOpened(featureId: Int, menu: android.view.Menu) =
            originalCallback.onMenuOpened(featureId, menu)

        override fun onMenuItemSelected(featureId: Int, item: android.view.MenuItem) =
            originalCallback.onMenuItemSelected(featureId, item)

        override fun onWindowAttributesChanged(attrs: android.view.WindowManager.LayoutParams) =
            originalCallback.onWindowAttributesChanged(attrs)

        override fun onContentChanged() = originalCallback.onContentChanged()
        override fun onWindowFocusChanged(hasFocus: Boolean) =
            originalCallback.onWindowFocusChanged(hasFocus)

        override fun onAttachedToWindow() = originalCallback.onAttachedToWindow()
        override fun onDetachedFromWindow() = originalCallback.onDetachedFromWindow()
        override fun onPanelClosed(featureId: Int, menu: android.view.Menu) =
            originalCallback.onPanelClosed(featureId, menu)

        override fun onSearchRequested() = originalCallback.onSearchRequested()
        override fun onSearchRequested(event: android.view.SearchEvent) =
            originalCallback.onSearchRequested(event)

        override fun onWindowStartingActionMode(callback: android.view.ActionMode.Callback) =
            originalCallback.onWindowStartingActionMode(callback)

        override fun onWindowStartingActionMode(
            callback: android.view.ActionMode.Callback,
            type: Int
        ) =
            originalCallback.onWindowStartingActionMode(callback, type)

        override fun onActionModeStarted(mode: android.view.ActionMode) =
            originalCallback.onActionModeStarted(mode)

        override fun onActionModeFinished(mode: android.view.ActionMode) =
            originalCallback.onActionModeFinished(mode)

    }

    /**
     * This class is used to track touch events globally in an Activity.
     * It logs the touch events and can be extended to integrate with an analytics system.
     */

    // Store the original callback to delegate other window events
    private val originalCallback: Callback = activity.window.callback

    init {
        // Set the new callback to the activity's window

        Logger.logLevel = config.logLevel
        // isLoggingEnabled is now handled by BaseManager
    }

    @Volatile
    private var isTrackingEnabled: Boolean = true

    /**
     * Enable tracking
     */
    fun enableTracking() {
        isTrackingEnabled = true
    }

    /**
     * Disable tracking
     */
    fun disableTracking() {
        isTrackingEnabled = false
    }

    //#region Base Manager actions
    override fun start() {
        activity.window.callback = callback
    }

    override fun stop() {
        activity.window.callback = null
    }

    override fun pause() {
        stop()
    }

    override fun resume() {
        start()
    }
    //#endregion

}
