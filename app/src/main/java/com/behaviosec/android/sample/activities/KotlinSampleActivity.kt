package com.behaviosec.android.sample.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.behaviosec.android.sample.databinding.ActivityXmlSampleBinding // Using the correct binding class
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

class KotlinSampleActivity : AppCompatActivity() {

    private val binding: ActivityXmlSampleBinding by lazy {
        ActivityXmlSampleBinding.inflate(layoutInflater)
    }

    private val userBehaviorCoreSDK: UserBehaviorCoreSDK by lazy { get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAccelerometerManager()
        setupTouchManager()
        setupViewTouchManager()
    }

    private fun setupAccelerometerManager() {
        val accelerometerManager =
            userBehaviorCoreSDK.getAccelerometerManager()
        accelerometerManager.setEnabled(true).setDebugMode(true).setLoggingEnabled(true)

        accelerometerManager.addListener(object : AccelerometerListener {
            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                binding.accelerometerAccuracy.text = model.toMessage()
            }

            override fun onSensorChanged(model: AccelerometerEventModel) {
                binding.accelerometerSensor.text = model.toMessage()

            }
        })

        accelerometerManager.addErrorListener(object : AccelerometerErrorListener {
            override fun onError(error: BaseUserBehaviorException) {
                val msg = error.toMessage()
                Log.e("AccelerometerManager", msg)
                binding.accelerometerAccuracy.text = msg
                binding.accelerometerSensor.text = msg
            }
        })

        binding.startAccelerometerButton.setOnClickListener { accelerometerManager.start() }
        binding.stopAccelerometerButton.setOnClickListener { accelerometerManager.stop() }
    }

    private fun setupTouchManager() {
        val activityTouchManager =
            userBehaviorCoreSDK.fetchOrCreateActivityTouchManager(this)

        activityTouchManager.addListener(object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                val msg = event.toMessage()
                Log.d("ActivityTouchManager", msg)
                binding.touchDetails.text = msg
                return true
            }
        })

        activityTouchManager.addErrorListener(object : TouchErrorListener {
            override fun onError(error: BaseUserBehaviorException) {
                val msg = error.toMessage()
                Log.e("ActivityTouchManager", msg)
                binding.touchDetails.text = msg
            }
        })

        binding.startTouchButton.setOnClickListener { activityTouchManager.start() }
        binding.stopTouchButton.setOnClickListener { activityTouchManager.stop() }
    }

    private fun setupViewTouchManager() {
        val viewTouchManager =
            userBehaviorCoreSDK.fetchOrCreateViewTouchManager(binding.greenView);

        viewTouchManager.addListener(object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                val msg = event.toMessage()
                Log.d("ActivityTouchManager", msg)
                binding.touchViewDetails.text = msg
                return true
            }
        })

        viewTouchManager.addErrorListener(object : TouchErrorListener {
            override fun onError(error: BaseUserBehaviorException) {
                val msg = error.toMessage()
                Log.e("ActivityTouchManager", msg)
                binding.touchViewDetails.text = msg
            }
        })

        binding.startTouchViewButton.setOnClickListener {
            viewTouchManager.start()
        }
        binding.stopTouchViewButton.setOnClickListener {
            viewTouchManager.stop()
        }

    }

}