package com.behaviosec.android.userBehaviorSDK.managers

import android.app.Activity
import android.app.Application
import android.view.View
import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig
import com.behaviosec.android.userBehaviorSDK.managers.touchs.ActivityTouchManager
import com.behaviosec.android.userBehaviorSDK.managers.touchs.AppTouchManager
import com.behaviosec.android.userBehaviorSDK.managers.touchs.ViewTouchManager

/**
 * NOTE: You should rename this file to `TouchManager.kt`.
 *
 * This is the main entry point for tracking touch events. It uses a builder to create a manager
 * for a specific scope, such as an entire Activity or a specific View.
 *
 * To track all touches in an activity:
 * val touchManager = TouchManager.Builder(myActivity).build()
 * touchManager.start()
 *
 * To track touches only on a specific view:
 * val touchManager = TouchManager.Builder(myActivity).forView(myButton).build()
 * touchManager.start()
 */
class TouchManager private constructor(
    private val internalManager: ITouchManager
) : ITouchManager by internalManager {

    class Builder() {

        private var app: Application? = null
        private var activity: Activity? = null

        private var targetView: View? = null
        private var config: TouchTrackerConfig = TouchTrackerConfig()

        fun forApp(app: Application) = apply {
            this.app = app
        }

        fun forActivity(activity: Activity) = apply {
            this.activity = activity
        }

        fun forView(view: View) = apply {
            this.targetView = view
        }

        fun withConfig(config: TouchTrackerConfig) = apply {
            this.config = config
        }

        fun build(): TouchManager {
            if (app == null && activity == null && targetView == null) {
                throw IllegalArgumentException("Builder requires an Application, Activity, or View to create a TouchManager.")
            }
            val manager = if (app != null) {
                AppTouchManager(app!!, config)
            } else if (activity != null) {
                ActivityTouchManager(activity!!, config)
            } else {
                ViewTouchManager(targetView!!, config)
            }
            return TouchManager(manager)
        }
    }
}

