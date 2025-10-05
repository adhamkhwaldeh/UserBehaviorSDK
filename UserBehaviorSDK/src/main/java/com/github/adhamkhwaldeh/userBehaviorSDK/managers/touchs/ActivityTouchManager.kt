package com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs

import android.app.Activity
import android.view.ActionMode
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.SearchEvent
import android.view.View
import android.view.Window.Callback
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.github.adhamkhwaldeh.userBehaviorSDK.helpers.DateHelpers
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.R
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.Logger
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.BaseManager

/**
 * Activity touch manager
 *
 * @constructor
 *
 * @param activity
 * @param config
 */
internal class ActivityTouchManager private constructor(
    val activity: Activity,
    logger: Logger,
    config: TouchConfig = TouchConfig(),
) : BaseManager<TouchListener, TouchErrorListener, TouchConfig>(config, logger), ITouchManager {

    companion object {
        @JvmSynthetic
        internal fun create(
            activity: Activity,
            logger: Logger,
            config: TouchConfig = TouchConfig(),
        ): ActivityTouchManager = ActivityTouchManager(
            activity = activity,
            config = config,
            logger = logger
        )
    }

    private val callback = object : Callback {
        override fun dispatchTouchEvent(event: MotionEvent): Boolean {

            logger.d(
                activity.getString(R.string.global_touch_tracker),
                activity.getString(
                    R.string.touch_event_action_xy,
                    event.action,
                    event.x,
                    event.y
                ),
                config
            )

            // If the manager is disabled, just delegate the event without logging
            if (config.isEnabled && listeners.isNotEmpty()) {
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
        override fun dispatchKeyEvent(event: KeyEvent) =
            originalCallback.dispatchKeyEvent(event)

        override fun dispatchKeyShortcutEvent(event: KeyEvent) =
            originalCallback.dispatchKeyShortcutEvent(event)

        override fun dispatchTrackballEvent(event: MotionEvent) =
            originalCallback.dispatchTrackballEvent(event)

        override fun dispatchGenericMotionEvent(event: MotionEvent) =
            originalCallback.dispatchGenericMotionEvent(event)

        override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent) =
            originalCallback.dispatchPopulateAccessibilityEvent(event)

        override fun onCreatePanelView(featureId: Int) =
            originalCallback.onCreatePanelView(featureId)

        override fun onCreatePanelMenu(featureId: Int, menu: Menu) =
            originalCallback.onCreatePanelMenu(featureId, menu)

        override fun onPreparePanel(
            featureId: Int,
            view: View?,
            menu: Menu
        ) =
            originalCallback.onPreparePanel(featureId, view, menu)

        override fun onMenuOpened(featureId: Int, menu: Menu) =
            originalCallback.onMenuOpened(featureId, menu)

        override fun onMenuItemSelected(featureId: Int, item: MenuItem) =
            originalCallback.onMenuItemSelected(featureId, item)

        override fun onWindowAttributesChanged(attrs: WindowManager.LayoutParams) =
            originalCallback.onWindowAttributesChanged(attrs)

        override fun onContentChanged() = originalCallback.onContentChanged()
        override fun onWindowFocusChanged(hasFocus: Boolean) =
            originalCallback.onWindowFocusChanged(hasFocus)

        override fun onAttachedToWindow() = originalCallback.onAttachedToWindow()
        override fun onDetachedFromWindow() = originalCallback.onDetachedFromWindow()
        override fun onPanelClosed(featureId: Int, menu: Menu) =
            originalCallback.onPanelClosed(featureId, menu)

        override fun onSearchRequested() = originalCallback.onSearchRequested()
        override fun onSearchRequested(event: SearchEvent) =
            originalCallback.onSearchRequested(event)

        override fun onWindowStartingActionMode(callback: ActionMode.Callback) =
            originalCallback.onWindowStartingActionMode(callback)

        override fun onWindowStartingActionMode(
            callback: ActionMode.Callback,
            type: Int
        ) =
            originalCallback.onWindowStartingActionMode(callback, type)

        override fun onActionModeStarted(mode: ActionMode) =
            originalCallback.onActionModeStarted(mode)

        override fun onActionModeFinished(mode: ActionMode) =
            originalCallback.onActionModeFinished(mode)

    }

    /**
     * This class is used to track touch events globally in an Activity.
     * It logs the touch events and can be extended to integrate with an analytics system.
     */

    // Store the original callback to delegate other window events
    private val originalCallback: Callback = activity.window.callback

    //#region Base Manager actions
    var isManagerStarted = false

    override fun isStarted(): Boolean {
        return isManagerStarted//activity.window.callback == callback
    }

    override fun start() {
        activity.window.callback = callback
        isManagerStarted = true
    }

    override fun stop() {
        // Restore the original callback only if ours is still the current one.
        if (activity.window.callback === callback) {
            activity.window.callback = originalCallback
        }
        isManagerStarted = false
    }

    override fun pause() {
        stop()
    }

    override fun resume() {
        start()
    }
    //#endregion

}
