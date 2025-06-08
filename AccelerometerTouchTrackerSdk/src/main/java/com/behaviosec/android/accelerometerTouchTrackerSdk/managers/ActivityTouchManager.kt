package com.behaviosec.android.accelerometerTouchTrackerSdk.managers

import android.app.Activity
import android.util.Log
import android.view.MotionEvent
import android.view.Window.Callback
import com.behaviosec.android.accelerometerTouchTrackerSdk.helpers.DateHelpers
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.ActivityTouchListener

/**
 * Activity touch manager
 *
 * @constructor
 *
 * @param activity
 */
class ActivityTouchManager(activity: Activity) : BaseManager(), Callback {

    /**
     * This class is used to track touch events globally in an Activity.
     * It logs the touch events and can be extended to integrate with an analytics system.
     */


    // Store the original callback to delegate other window events
    private val originalCallback: Callback = activity.window.callback

    init {
        // Set the new callback to the activity's window
        activity.window.callback = this
    }

    private var activityTouchListener: ActivityTouchListener? = null

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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (isLoggingEnabled && isDebugMode) {
            Log.d(
                "GlobalTouchTracker",
                "TouchEvent → action=${event.action} x=${event.x} y=${event.y}"
            )
            // If the manager is disabled, just delegate the event without logging
        }
        // 👇 Log the global touch event
        if (activityTouchListener != null && isEnabled) {
            val continueBase =
                activityTouchListener!!.dispatchTouchEvent(event, DateHelpers.getCurrentDate())
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
