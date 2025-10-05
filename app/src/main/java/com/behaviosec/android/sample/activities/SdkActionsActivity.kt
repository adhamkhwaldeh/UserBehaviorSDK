package com.behaviosec.android.sample.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.behaviosec.android.sample.databinding.ActivitySdkActionsBinding
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import org.koin.android.ext.android.get

class SdkActionsActivity : AppCompatActivity() {

    // Use lazy initialization for both binding and the SDK
    private val binding: ActivitySdkActionsBinding by lazy {
        ActivitySdkActionsBinding.inflate(layoutInflater)
    }
    private val userBehaviorCoreSDK: UserBehaviorCoreSDK by lazy { get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Setup individual managers to see the effect of global controls
        setupAccelerometerManager()
        setupTouchManager()

        // Setup global SDK action buttons
        setupGlobalSdkActions()
    }

    private fun setupGlobalSdkActions() {
        binding.startAllButton.setOnClickListener {
            Log.d("SDK_ACTIONS", "Starting all managers...")
            userBehaviorCoreSDK.startAll()
        }
        binding.stopAllButton.setOnClickListener {
            Log.d("SDK_ACTIONS", "Stopping all managers...")
            userBehaviorCoreSDK.stopAll()
        }
        binding.pauseAllButton.setOnClickListener {
            Log.d("SDK_ACTIONS", "Pausing all managers...")
            userBehaviorCoreSDK.pauseAll()
        }
        binding.resumeAllButton.setOnClickListener {
            Log.d("SDK_ACTIONS", "Resuming all managers...")
            userBehaviorCoreSDK.resumeAll()
        }
    }

    private fun setupAccelerometerManager() {
        val accelerometerManager = userBehaviorCoreSDK.getAccelerometerManager(AccelerometerConfig())
        accelerometerManager.setDebugMode(true).setLoggingEnabled(true)

        accelerometerManager.addListener(object : AccelerometerListener {
            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                binding.accelerometerDetails.text = "Accelerometer Accuracy: ${model.accuracy}"
            }

            override fun onSensorChanged(model: AccelerometerEventModel) {
                // To avoid flooding the UI, you can show less frequent updates if needed
            }
        })

        accelerometerManager.addErrorListener(object : AccelerometerErrorListener {
            override fun onError(error: ManagerErrorModel) {
                Log.e("SDK_ACTIONS", "Accelerometer Error: ${error.message}")
                binding.accelerometerDetails.text = "Accelerometer Error: ${error.message}"
            }
        })
    }

    private fun setupTouchManager() {
        val activityTouchManager = userBehaviorCoreSDK.fetchOrCreateActivityTouchManager(this, TouchConfig())

        activityTouchManager.addListener(object : TouchListener {
            override fun dispatchTouchEvent(model: MotionEventModel): Boolean {
                binding.touchDetails.text = "Touch Event at: ${model.date}"
                return true // Return true to indicate the event was handled
            }
        })

        activityTouchManager.addErrorListener(object : TouchErrorListener {
            override fun onError(error: ManagerErrorModel) {
                Log.e("SDK_ACTIONS", "Touch Error: ${error.message}")
                binding.touchDetails.text = "Touch Error: ${error.message}"
            }
        })
    }

    override fun onStop() {
        super.onStop()
        // It's a good practice to stop all managers when the activity is no longer visible
        // to conserve resources.
        userBehaviorCoreSDK.stopAll()
    }
}
