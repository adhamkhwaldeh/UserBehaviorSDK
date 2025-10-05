package com.behaviosec.android.sample.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.behaviosec.android.sample.R
import com.behaviosec.android.sample.databinding.ActivityXmlSampleBinding
import com.behaviosec.android.sample.helpers.toMessage
import com.behaviosec.android.sample.viewModels.LiveDataViewModel
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.exceptions.BaseUserBehaviorException
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel

import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.AccelerometerResult
import org.koin.android.ext.android.get

class LiveDataSampleActivity : AppCompatActivity() {
    private val binding: ActivityXmlSampleBinding by lazy {
        ActivityXmlSampleBinding.inflate(layoutInflater)
    }
    private val liveDataViewModel: LiveDataViewModel by lazy { LiveDataViewModel(get()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAccelerometerManager()

        setupTouchManager()

        setupViewTouchManager()
    }

    private fun setupAccelerometerManager() {
        binding.startAccelerometerButton.setOnClickListener { liveDataViewModel.startTracking() }
        binding.stopAccelerometerButton.setOnClickListener { liveDataViewModel.stopTracking() }

        // Example: Observe LiveData and update UI
        liveDataViewModel.accelerometerResult.observe(this, Observer { result ->
            result.fold(onSuccess = { accel ->
                when (accel) {
                    is AccelerometerResult.SensorChanged -> {
                        binding.accelerometerSensor.text = accel.model.toMessage()
                    }

                    is AccelerometerResult.AccuracyChanged -> {
                        binding.accelerometerAccuracy.text = accel.model.toMessage()
                    }
                }
            }, onFailure = { error ->
                val msg = BaseUserBehaviorException.fromException(error).toMessage()
                Log.e("AccelerometerManager", msg)
            })
        })

        liveDataViewModel.lastAccelerometerEvent.observe(this, Observer { event ->

        })

        liveDataViewModel.lastAccuracyEvent.observe(this, Observer { accuracy ->

        })

        liveDataViewModel.accelerometerError.observe(this, Observer { error ->

        })

    }

    private fun setupTouchManager() {
        binding.startTouchButton.setOnClickListener {
            liveDataViewModel.startActivityTouchTracking(this@LiveDataSampleActivity)
        }
        binding.stopTouchButton.setOnClickListener {
            liveDataViewModel.stopActivityTouchTracking()
        }

        // Example: Observe LiveData and update UI
        liveDataViewModel.activityTouchResult.observe(this, Observer { result ->
            result.fold(onSuccess = { res ->
                val msg = res.toMessage()
                binding.touchDetails.text = msg
            }, onFailure = { error ->
                val msg = BaseUserBehaviorException.fromException(error).toMessage()
                Log.e("ActivityTouchManager", msg)
            })
        })
    }

    private fun setupViewTouchManager() {
        binding.startTouchButton.setOnClickListener {
            liveDataViewModel.startViewTouchTracking(binding.greenView)
        }
        binding.stopTouchButton.setOnClickListener {
            liveDataViewModel.stopViewTouchTracking()
        }

        // Example: Observe LiveData and update UI
        liveDataViewModel.viewTouchResult.observe(this, Observer { result ->
            result.fold(onSuccess = { res ->
                val msg = res.toMessage()
                Log.d("ActivityTouchManager", msg)
                binding.touchViewDetails.text = msg
            }, onFailure = { error ->
                val msg = BaseUserBehaviorException.fromException(error).toMessage()
                Log.e("ViewTouchManager", msg)
            })
        })

    }

}