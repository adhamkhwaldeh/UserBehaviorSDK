package com.github.adhamkhwaldeh.userBehaviorSDK.models

import android.app.Activity
import android.app.Application
import android.view.View

sealed interface ManagerKey
object ManagerAccelerometerKey : ManagerKey

sealed interface ManagerTouchKey : ManagerKey
data class ManagerActivityKey(val activity: Activity) : ManagerTouchKey
data class ManagerViewKey(val view: View) : ManagerTouchKey
data class ManagerApplicationKey(val application: Application) : ManagerTouchKey

