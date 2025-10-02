package com.behaviosec.android.userBehaviorSDK.models

import android.app.Activity
import android.app.Application
import android.view.View

sealed class ManagerKey
object ManageAccelerometerKey : ManagerKey()
data class ManageActivityKey(val activity: Activity) : ManagerKey()
data class ManageViewKey(val view: View) : ManagerKey()
data class ManageApplicationKey(val application: Application) : ManagerKey()

//data class UiKey(val scope: Any) : ManagerKey() {
//    init {
//        require(scope is Activity || scope is View) { "Scope must be an Activity or a View" }
//    }
//}
