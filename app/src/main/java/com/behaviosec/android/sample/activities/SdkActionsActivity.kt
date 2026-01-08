package com.behaviosec.android.sample.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.behaviosec.android.sample.databinding.ActivitySdkActionsBinding
import com.behaviosec.android.sample.helpers.toMessage
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseUserBehaviorException
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
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
        setupViewTouchManager()
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
        val accelerometerManager =
            userBehaviorCoreSDK.getAccelerometerManager()
        accelerometerManager.setDebugMode(true).setLoggingEnabled(true)

        accelerometerManager.addListener(object : AccelerometerListener {
            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                binding.defaultXmlLayout.accelerometerAccuracy.text = model.toMessage()
            }

            override fun onSensorChanged(model: AccelerometerEventModel) {
                binding.defaultXmlLayout.accelerometerSensor.text = model.toMessage()
            }
        })

        accelerometerManager.addErrorListener(object : AccelerometerErrorListener {
            override fun onError(error: BaseUserBehaviorException) {
                Log.e("SDK_ACTIONS", "Accelerometer Error: ${error.message}")
            }
        })

        binding.defaultXmlLayout.startAccelerometerButton.setOnClickListener {
            accelerometerManager.start()
        }
        binding.defaultXmlLayout.stopAccelerometerButton.setOnClickListener {
            accelerometerManager.stop()
        }

    }

    private fun setupTouchManager() {
        val activityTouchManager =
            userBehaviorCoreSDK.fetchOrCreateActivityTouchManager(this)

        activityTouchManager.addListener(object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                binding.defaultXmlLayout.touchDetails.text = event.toMessage()
                return true // Return true to indicate the event was handled
            }
        })

        activityTouchManager.addErrorListener(object : TouchErrorListener {
            override fun onError(error: BaseUserBehaviorException) {
                Log.e("SDK_ACTIONS", "Touch Error: ${error.message}")
            }
        })
        binding.defaultXmlLayout.startTouchButton.setOnClickListener {
            activityTouchManager.start()
        }
        binding.defaultXmlLayout.stopTouchButton.setOnClickListener {
            activityTouchManager.stop()
        }

    }

    private fun setupViewTouchManager() {
        val viewTouchManager =
            userBehaviorCoreSDK.fetchOrCreateViewTouchManager(
                binding.defaultXmlLayout.greenView,
            )

        viewTouchManager.addListener(object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                val msg = event.toMessage()
                Log.d("ActivityTouchManager", msg)
                binding.defaultXmlLayout.touchViewDetails.text = msg
                return true
            }
        })

        viewTouchManager.addErrorListener(object : TouchErrorListener {
            override fun onError(error: BaseUserBehaviorException) {
                val msg = error.toMessage()
                Log.e("ActivityTouchManager", msg)
            }
        })

        binding.defaultXmlLayout.startTouchViewButton.setOnClickListener {
            viewTouchManager.start()
        }
        binding.defaultXmlLayout.stopTouchViewButton.setOnClickListener {
            viewTouchManager.stop()
        }

    }

    override fun onStop() {
        super.onStop()
        // It's a good practice to stop all managers when the activity is no longer visible
        // to conserve resources.
        userBehaviorCoreSDK.stopAll()
    }
}
