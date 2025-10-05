package com.behaviosec.android.sample.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text
import androidx.lifecycle.lifecycleScope
import com.behaviosec.android.sample.databinding.ActivityXmlSampleBinding
import com.behaviosec.android.sample.helpers.toMessage
import com.behaviosec.android.sample.viewModels.CoroutineViewModel
import com.behaviosec.android.sample.viewModels.LiveDataViewModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date

class CoroutineSampleActivity : AppCompatActivity() {

    private val coroutineViewModel: CoroutineViewModel by lazy { CoroutineViewModel(get()) }

    private val binding: ActivityXmlSampleBinding by lazy {
        ActivityXmlSampleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        // --- Accelerometer Observers ---
        lifecycleScope.launch {
            coroutineViewModel.lastAccelerometerEvent.collectLatest { event ->
                event?.let { binding.accelerometerSensor.text = it.toMessage() }
            }
        }

        lifecycleScope.launch {
            coroutineViewModel.lastAccuracyEvent.collectLatest { accuracy ->
                accuracy?.let { binding.accelerometerAccuracy.text = it.toMessage() }
            }
        }

        lifecycleScope.launch {
            coroutineViewModel.accelerometerError.collectLatest { error ->
                error?.let {
                    val msg = it.toMessage()
                    Log.e("CoroutineSample", "Accelerometer Error: $msg")
                    binding.accelerometerAccuracy.text = msg
                    binding.accelerometerSensor.text = msg
                }
            }
        }

        // --- Touch Manager Observers ---
        lifecycleScope.launch {
            coroutineViewModel.activityTouchResult.collectLatest { result ->
                result?.fold(
                    onSuccess = { res ->
                        val msg = res.toMessage()
                        Log.d("CoroutineSample", "Activity Touch: $msg")
                        binding.touchDetails.text = "Activity: $msg"
                    },
                    onFailure = { error ->
                        val msg = ManagerErrorModel.fromException(error).toMessage()
                        binding.touchDetails.text = msg
                    }
                )
            }
        }

        lifecycleScope.launch {
            coroutineViewModel.viewTouchResult.collectLatest { result ->
                result?.fold(
                    onSuccess = { res ->
                        val msg = res.toMessage()
                        Log.d("CoroutineSample", "View Touch: $msg")
                        binding.touchViewDetails.text =
                            "View: $msg" // Assuming you have a new TextView
                    },
                    onFailure = { error ->
                        val msg = ManagerErrorModel.fromException(error).toMessage()
                        binding.touchViewDetails.text = msg
                    }
                )
            }
        }
    }

    private fun setupClickListeners() {
        // Accelerometer
        binding.startAccelerometerButton.setOnClickListener {
            coroutineViewModel.startTracking()
        }
        binding.stopAccelerometerButton.setOnClickListener {
            coroutineViewModel.stopTracking()
        }

        // Activity Touch
        binding.startTouchButton.setOnClickListener {
            coroutineViewModel.startActivityTouchTracking(this)
        }
        binding.stopTouchButton.setOnClickListener {
            coroutineViewModel.stopActivityTouchTracking()
        }

        // View Touch - Let's use the 'start' button as the target view
        binding.startTouchViewButton.setOnClickListener {
            // The view itself is passed to the ViewModel
            coroutineViewModel.startViewTouchTracking(binding.greenView)
        }
        binding.stopTouchViewButton.setOnClickListener {
            coroutineViewModel.stopViewTouchTracking()
        }
    }


}
