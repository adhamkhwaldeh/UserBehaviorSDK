package com.github.adhamkhwaldeh.userBehaviorSDK.managers

import android.app.Activity
import android.app.Application
import android.view.View
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.IBaseManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ActivityTouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.AppTouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ViewTouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerActivityKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerApplicationKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerTouchKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerViewKey

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
internal class TouchManager private constructor(
    private val internalManager: IBaseManager<TouchListener, TouchErrorListener, TouchConfig>
) : IBaseManager<TouchListener, TouchErrorListener, TouchConfig> by internalManager {

    class Builder() {

        private var app: Application? = null
        private var activity: Activity? = null

        private var targetView: View? = null
        private var config: TouchConfig = TouchConfig()

        fun forManagerKey(managerKey: ManagerTouchKey) = apply {
            when (managerKey) {
                is ManagerActivityKey -> forActivity(managerKey.activity)
                is ManagerApplicationKey -> forApp(managerKey.application)
                is ManagerViewKey -> forView(managerKey.view)
            }
        }

        fun forApp(app: Application) = apply {
            this.app = app
        }

        fun forActivity(activity: Activity) = apply {
            this.activity = activity
        }

        fun forView(view: View) = apply {
            this.targetView = view
        }

        fun withConfig(config: TouchConfig) = apply {
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

