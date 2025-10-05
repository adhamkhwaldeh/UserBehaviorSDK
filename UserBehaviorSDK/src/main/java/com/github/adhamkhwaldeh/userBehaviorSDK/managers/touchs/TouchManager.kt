package com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs

import android.app.Activity
import android.app.Application
import android.view.View
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.Logger
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
    private val internalManager: ITouchManager
) : ITouchManager by internalManager {

    internal class Builder(private val logger: Logger, private var config: TouchConfig) {

        private var app: Application? = null
        private var activity: Activity? = null

        private var targetView: View? = null


        @JvmSynthetic
        internal fun forManagerKey(managerKey: ManagerTouchKey) = apply {
            when (managerKey) {
                is ManagerActivityKey -> forActivity(managerKey.activity)
                is ManagerApplicationKey -> forApp(managerKey.application)
                is ManagerViewKey -> forView(managerKey.view)
            }
        }

        @JvmSynthetic
        fun forApp(app: Application) = apply {
            this.app = app
        }

        @JvmSynthetic
        fun forActivity(activity: Activity) = apply {
            this.activity = activity
        }

        @JvmSynthetic
        fun forView(view: View) = apply {
            this.targetView = view
        }

        @JvmSynthetic
        fun withConfig(config: TouchConfig) = apply {
            this.config = config
        }

        @JvmSynthetic
        fun build(): TouchManager {
            if (app == null && activity == null && targetView == null) {
                throw IllegalArgumentException("Builder requires an Application, Activity, or View to create a TouchManager.")
            }
            val manager = if (app != null) {
                ApplicationTouchManager.create(app!!, logger, config)
            } else if (activity != null) {
                ActivityTouchManager.create(activity!!, logger, config)
            } else {
                ViewTouchManager.create(targetView!!, logger, config)
            }
            return TouchManager(manager)
        }
    }

}