package com.behaviosec.android.sample.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.behaviosec.android.sample.databinding.ActivitySensorSampleBinding
import com.behaviosec.android.sample.helpers.toMessage
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.SensorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.SensorErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerGyroscopeKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerMagnetometerKey
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorAccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorEventModel
import org.koin.android.ext.android.get

class SensorSampleActivity : AppCompatActivity() {
    private val binding: ActivitySensorSampleBinding by lazy {
        ActivitySensorSampleBinding.inflate(layoutInflater)
    }

    private val userBehaviorCoreSDK: UserBehaviorCoreSDK by lazy { get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAccelerometerManager()
        setupGyroscopeManager()
        setupMagnetometerManager()

        // Global controls
        binding.startAllButton.setOnClickListener { userBehaviorCoreSDK.startAll() }
        binding.stopAllButton.setOnClickListener { userBehaviorCoreSDK.stopAll() }
    }

    private fun setupAccelerometerManager() {
        val accelerometerManager =
            userBehaviorCoreSDK.getAccelerometerManager(AccelerometerConfig())
        accelerometerManager.setLoggingEnabled(true)

        accelerometerManager.addListener(object : AccelerometerListener {
            override fun onSensorChanged(model: AccelerometerEventModel) {
                binding.accelerometerData.text = model.toMessage()
            }

            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                /* Optional: Handle accuracy */
            }
        })

        accelerometerManager.addErrorListener(object : AccelerometerErrorListener {
            override fun onError(error: ManagerErrorModel) {
                binding.accelerometerData.text = error.toMessage()
                Log.e("SensorSample", error.toMessage())
            }
        })

        binding.startAccelerometer.setOnClickListener {
            accelerometerManager.start()
        }
        binding.stopAccelerometer.setOnClickListener {
            accelerometerManager.stop()
        }
    }

    private fun setupGyroscopeManager() {
        val gyroscopeManager = userBehaviorCoreSDK.fetchOrCreateSensorManager(ManagerGyroscopeKey)
        gyroscopeManager.setLoggingEnabled(true)

        gyroscopeManager.addListener(object : SensorListener {
            override fun onSensorChanged(model: SensorEventModel) {
                binding.gyroscopeData.text = model.toMessage()
            }

            override fun onAccuracyChanged(model: SensorAccuracyChangedModel) {

            }
        })

        gyroscopeManager.addErrorListener(object : SensorErrorListener {
            override fun onError(error: ManagerErrorModel) {
                binding.gyroscopeData.text = error.toMessage()
                Log.e("SensorSample", error.toMessage())
            }
        })

        binding.startGyroscope.setOnClickListener { gyroscopeManager.start() }
        binding.stopGyroscope.setOnClickListener { gyroscopeManager.stop() }
    }

    private fun setupMagnetometerManager() {
        val magnetometerManager = userBehaviorCoreSDK.fetchOrCreateSensorManager(
            ManagerMagnetometerKey
        )
        magnetometerManager.setLoggingEnabled(true)

        magnetometerManager.addListener(object : SensorListener {
            override fun onSensorChanged(model: SensorEventModel) {
                binding.magnetometerData.text = model.toMessage()
            }

            override fun onAccuracyChanged(model: SensorAccuracyChangedModel) {}
        })

        magnetometerManager.addErrorListener(object : SensorErrorListener {
            override fun onError(error: ManagerErrorModel) {
                binding.magnetometerData.text = error.toMessage()
                Log.e("SensorSample", error.toMessage())
            }
        })

        binding.startMagnetometer.setOnClickListener {
            magnetometerManager.start()
        }
        binding.stopMagnetometer.setOnClickListener {
            magnetometerManager.stop()
        }
    }


    override fun onStop() {
        super.onStop()
        // Good practice to stop all managers when the activity is not visible.
        userBehaviorCoreSDK.stopAll()
    }
}
