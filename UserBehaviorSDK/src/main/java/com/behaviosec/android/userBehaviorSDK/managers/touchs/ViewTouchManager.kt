package com.behaviosec.android.userBehaviorSDK.managers.touchs

import android.view.MotionEvent
import android.view.View
import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig
import com.behaviosec.android.userBehaviorSDK.helpers.DateHelpers
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.TouchListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.behaviosec.android.userBehaviorSDK.logging.Logger
import com.behaviosec.android.userBehaviorSDK.managers.ITouchManager
import com.behaviosec.android.userBehaviorSDK.managers.base.IBaseManager
import com.behaviosec.android.userBehaviorSDK.models.MotionEventModel
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Touch Manager
 *
 * NOTE: It is recommended to rename the file from 'ActivityTouchManager.kt' to 'TouchManager.kt'
 * to better reflect its new, more generic purpose.
 *
 * This class attaches directly to a specific View to track its touch events. This provides a more
 * targeted and lifecycle-aware way to handle touches compared to intercepting all window events.
 *
 * It can be "attached" by calling start() and "detached" by calling stop().
 *
 * To track touches on a specific view (e.g., a button or a layout):
 * val touchManager = TouchManager(targetView = myButton)
 *
 * To track touches in a fragment:
 * val touchManager = TouchManager(targetView = requireView())
 *
 * To track all touches in an activity (by attaching to its root content view):
 * val contentView = activity.findViewById<View>(android.R.id.content)
 * val touchManager = TouchManager(targetView = contentView)
 *
 * @param targetView The View to which the touch listener will be attached.
 * @param config Configuration for the manager.
 */
class ViewTouchManager(
    private val targetView: View,
    override val config: TouchTrackerConfig = TouchTrackerConfig(),
) : IBaseManager<TouchListener, TouchErrorListener>, ITouchManager {

    override val listeners: CopyOnWriteArrayList<TouchListener> =
        CopyOnWriteArrayList<TouchListener>()

    override val errorListeners: CopyOnWriteArrayList<TouchErrorListener> =
        CopyOnWriteArrayList<TouchErrorListener>()

    init {
        Logger.logLevel = config.logLevel
    }

    private val touchListener: View.OnTouchListener = object : View.OnTouchListener {

        override fun onTouch(v: View, event: MotionEvent?): Boolean {
            // Process the event only if the manager is enabled and has listeners.
            if (config.isEnabled && listeners.isNotEmpty()) {
                if (config.isLoggingEnabled && config.isDebugMode) {
                    Logger.d(
                        "TouchManager",
                        "Touch event on view ${v!!.id}: action=${event!!.action}, x=${event.x}, y=${event.y}"
                    )
                }

                val model = MotionEventModel(event!!, DateHelpers.getCurrentDate())
                var consumeEvent = false
                for (listener in listeners) {
                    try {
                        // A listener returns 'false' to indicate it has consumed the event.
                        if (!listener.dispatchTouchEvent(model)) {
                            consumeEvent = true
                        }
                    } catch (e: Exception) {
                        notifyErrorListeners(e)
                    }
                }

                // If any of our behavior listeners consumed the event, we return true.
                // Otherwise, return false to allow the event to propagate to other listeners or the view's own onTouchEvent.
                return consumeEvent
            }

            // If the manager is disabled or has no listeners, we don't consume the event.
            return false
        }

    }

    /**
     * Attaches the touch listener to the target view.
     */
    override fun start() {
        targetView.setOnTouchListener(touchListener)
    }

    /**
     * Detaches the touch listener from the target view.
     */
    override fun stop() {
        // To avoid removing a listener set by another part of the code after this manager was started,
        // it's safest to check if the listener is still 'this'. However, since there is no public
        // 'getOnTouchListener' method, clearing it is the most common and direct approach.
        targetView.setOnTouchListener(null)
    }

    override fun pause() {
        // In this listener-based approach, pause is equivalent to stop (detach).
        stop()
    }

    override fun resume() {
        // In this listener-based approach, resume is equivalent to start (attach).
        start()
    }

}
