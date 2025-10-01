package com.behaviosec.android.userBehaviorSDK.managers

import android.app.Activity
import android.view.MotionEvent
import android.view.Window.Callback
import com.behaviosec.android.userBehaviorSDK.helpers.DateHelpers
import com.behaviosec.android.userBehaviorSDK.listeners.ActivityTouchListener
import com.behaviosec.android.userBehaviorSDK.listeners.ActivityTouchErrorListener
import com.behaviosec.android.userBehaviorSDK.R
import com.behaviosec.android.userBehaviorSDK.logging.Logger
import com.behaviosec.android.userBehaviorSDK.models.MotionEventModel
import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig

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
) : BaseManager(config), Callback {

    /**
     * This class is used to track touch events globally in an Activity.
     * It logs the touch events and can be extended to integrate with an analytics system.
     */

    // Store the original callback to delegate other window events
    private val originalCallback: Callback = activity.window.callback

    init {
        // Set the new callback to the activity's window
        activity.window.callback = this
        Logger.logLevel = config.logLevel
        // isLoggingEnabled is now handled by BaseManager
    }

    private var activityTouchListener: ActivityTouchListener? = null

    @Volatile
    private var errorListener: ActivityTouchErrorListener? = null

    private val activityManagersLock = Any()

    /**
     * Set listener
     *
     * @param listener
     */
    fun setListener(listener: ActivityTouchListener) {
        activityTouchListener = listener
    }

    /**
     * Remove listener
     *
     */
    fun removeListener() {
        activityTouchListener = null
    }

    /**
     * Set error listener
     *
     * @param listener
     */
    fun setErrorListener(listener: ActivityTouchErrorListener) {
        synchronized(activityManagersLock) {
            this.errorListener = listener
        }
    }

    /**
     * Remove error listener
     *
     */
    fun removeErrorListener() {
        synchronized(activityManagersLock) {
            this.errorListener = null
        }
    }


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
        if (activityTouchListener != null && config.isEnabled) {
            val continueBase =
                activityTouchListener!!.dispatchTouchEvent(
                    MotionEventModel(
                        event,
                        DateHelpers.getCurrentDate()
                    )
                )
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

    override fun onCreatePanelView(featureId: Int) = originalCallback.onCreatePanelView(featureId)
    override fun onCreatePanelMenu(featureId: Int, menu: android.view.Menu) =
        originalCallback.onCreatePanelMenu(featureId, menu)

    override fun onPreparePanel(featureId: Int, view: android.view.View?, menu: android.view.Menu) =
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

    override fun onWindowStartingActionMode(callback: android.view.ActionMode.Callback, type: Int) =
        originalCallback.onWindowStartingActionMode(callback, type)

    override fun onActionModeStarted(mode: android.view.ActionMode) =
        originalCallback.onActionModeStarted(mode)

    override fun onActionModeFinished(mode: android.view.ActionMode) =
        originalCallback.onActionModeFinished(mode)
}
