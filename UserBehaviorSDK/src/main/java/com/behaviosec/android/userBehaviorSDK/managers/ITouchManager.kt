package com.behaviosec.android.userBehaviorSDK.managers

import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.TouchListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.behaviosec.android.userBehaviorSDK.managers.base.IBaseManager
import com.behaviosec.android.userBehaviorSDK.managers.base.IBaseCallbackManager
import com.behaviosec.android.userBehaviorSDK.managers.base.IBaseErrorManager

interface ITouchManager : IBaseManager<TouchListener, TouchErrorListener>