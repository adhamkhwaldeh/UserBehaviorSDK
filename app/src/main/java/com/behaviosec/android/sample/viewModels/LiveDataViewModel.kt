package com.behaviosec.android.sample.viewModels

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.AccelerometerResult
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accelerometerResultLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accuracyChangedEventsLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.errorsLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.sensorChangedLiveData
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.touchResultLiveData
import kotlinx.coroutines.launch

/**
 * LiveData-based ViewModel for sensor data.
 *
 * @property userBehaviorCoreSDK The main SDK entry point injected by Koin.
 */
class LiveDataViewModel(
    private val userBehaviorCoreSDK: UserBehaviorCoreSDK
) : ViewModel() {

    //#region Accelerometer
    private val accelerometerManager: IAccelerometerManager =
        userBehaviorCoreSDK.getAccelerometerManager()

    val lastAccelerometerEvent: LiveData<AccelerometerEventModel> =
        accelerometerManager.sensorChangedLiveData()

    val lastAccuracyEvent: LiveData<AccuracyChangedModel> =
        accelerometerManager.accuracyChangedEventsLiveData()

    val accelerometerError: LiveData<BaseSDKException> =
        accelerometerManager.errorsLiveData()

    val accelerometerResult: LiveData<Result<AccelerometerResult>> =
        accelerometerManager.accelerometerResultLiveData()

    /**
     * Starts tracking accelerometer data.
     */
    fun startTracking() {
        viewModelScope.launch {
            // It's good practice to enable logging for debugging
            accelerometerManager
                .setLoggingEnabled(true)
                .setDebugMode(true)
            accelerometerManager.start()
        }
    }

    /**
     * Stops tracking accelerometer data.
     */
    fun stopTracking() {
        viewModelScope.launch {
            accelerometerManager.stop()
        }
    }

    //#endregion

    //#region Touch Managers
    private val activityTouchManagerProvider = MutableLiveData<ITouchManager>()
    val activityTouchResult: LiveData<Result<MotionEventModel>> =
        activityTouchManagerProvider.switchMap {
            it.touchResultLiveData()
        }

    private val viewTouchManagerProvider = MutableLiveData<ITouchManager>()
    val viewTouchResult: LiveData<Result<MotionEventModel>> = viewTouchManagerProvider.switchMap {
        it.touchResultLiveData()
    }

    fun startActivityTouchTracking(activity: Activity) {
        viewModelScope.launch {
            val manager = userBehaviorCoreSDK.fetchOrCreateActivityTouchManager(activity)
            manager.start()
            activityTouchManagerProvider.postValue(manager)
        }
    }

    fun stopActivityTouchTracking() {
        viewModelScope.launch {
            activityTouchManagerProvider.value?.stop()
        }
    }

    /**
     * NOTE: A ViewModel should typically not hold a direct reference to a View.
     * This method is provided for completeness, but it is the caller's responsibility
     * to call `stopViewTouchTracking` when the view is destroyed to prevent memory leaks.
     * Consider using the `collectViewTouchEvents` Modifier in Compose for a safer alternative.
     */
    fun startViewTouchTracking(view: View) {
        viewModelScope.launch {
            val manager = userBehaviorCoreSDK.fetchOrCreateViewTouchManager(view)
            manager.start()
            viewTouchManagerProvider.postValue(manager)
        }
    }

    fun stopViewTouchTracking() {
        viewModelScope.launch {
            viewTouchManagerProvider.value?.stop()
        }
    }
    //#endregion

    /**
     * Clean up resources when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        // Ensure managers are stopped to prevent leaks
        stopTracking()
        stopActivityTouchTracking()
        stopViewTouchTracking()
    }

}
