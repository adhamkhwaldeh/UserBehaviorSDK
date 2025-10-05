package com.behaviosec.android.sample.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.behaviosec.android.sample.R
import com.behaviosec.android.sample.helpers.toMessage
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.exceptions.BaseUserBehaviorException
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.ProvideUserBehaviorSDK
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.collectViewTouchEvents
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.rememberAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.rememberActivityTouchManager
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.AccelerometerResult
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accelerometerResultFlow
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.touchResultFlow
import org.koin.android.ext.android.get

class ComposeSampleActivity : ComponentActivity() {

    private val userBehaviorCoreSDK: UserBehaviorCoreSDK by lazy { get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Provide the SDK instance to the composable tree
            ProvideUserBehaviorSDK(userBehaviorCoreSDK) {
                ComposeSampleScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeSampleScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Compose Sample (SDK Extensions)") }) }
    ) { padding ->
        SensorScreen(modifier = Modifier.padding(padding))
    }
}

@Composable
fun SensorScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    // --- State Management within the Composable ---
    var accelerometerData by remember { mutableStateOf<Result<AccelerometerResult>?>(null) }
    var activityTouchData by remember { mutableStateOf<Result<MotionEventModel>?>(null) }
    var viewTouchData by remember { mutableStateOf<Result<MotionEventModel>?>(null) }

    // --- Manager and Event Collection ---
    // 1. Remember and manage the Accelerometer manager's lifecycle
    val accelerometerManager = rememberAccelerometerManager()
    LaunchedEffect(accelerometerManager) {
        accelerometerManager.accelerometerResultFlow().collect {
            accelerometerData = it
        }
    }

    // 2. Remember and manage the Activity Touch manager's lifecycle
    val activityTouchManager = rememberActivityTouchManager()
    LaunchedEffect(activityTouchManager) {
        activityTouchManager.touchResultFlow().collect {
            activityTouchData = it
        }
    }

    // 1. Create a state variable to control the collection
    var isViewTouchEnabled by remember { mutableStateOf(false) }

    // --- UI Layout ---
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.field_margin))
            .verticalScroll(scrollState)
    ) {
        // --- Accelerometer Section ---
        Text(
            text = "Accelerometer",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        // Buttons are now just for display, as managers auto-start/stop
        DisplayButtons(onStartClick = {
            accelerometerManager.start()
        }, onStopClick = {
            accelerometerManager.stop()
        })
        // Display formatted data
        val (accelMsg, accelColor) = formatAccelerometerResult(accelerometerData)
        Text(text = accelMsg, color = accelColor)

        // --- Touch Activity Section ---
        Text(
            text = "Touch Activity",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )
        DisplayButtons(onStartClick = {
            activityTouchManager.start()
        }, onStopClick = {
            activityTouchManager.stop()
        })
        val (activityMsg, activityColor) = formatTouchResult(activityTouchData)
        Text(text = activityMsg, color = activityColor)


        // --- Touch Green View Section ---
        Text(
            text = "Touch Green View",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )
        DisplayButtons(
            onStartClick = {
                isViewTouchEnabled = true
            },
            onStopClick = {
                isViewTouchEnabled = false
            },
        )
        val (viewMsg, viewColor) = formatTouchResult(viewTouchData)
        Text(text = viewMsg, color = viewColor)

        // 3. Use the modifier to collect touch events for this specific view
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(200.dp)
                .padding(top = 8.dp)
                .background(Color(0xFF388E3C)) // holo_green_dark
                .collectViewTouchEvents(enabled = isViewTouchEnabled) { result ->
                    viewTouchData = result
                }
        )
    }
}

@Composable
private fun DisplayButtons(
    startText: String = "Start",
    stopText: String = "Stop",
    onStartClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = onStartClick, modifier = Modifier.weight(1f), enabled = true) {
            Text(startText)
        }
        Button(onClick = onStopClick, modifier = Modifier.weight(1f), enabled = true) {
            Text(stopText)
        }
    }
}

private fun formatAccelerometerResult(result: Result<AccelerometerResult>?): Pair<String, Color> {
    if (result == null) return "ACC: Idle" to Color.Black
    return result.fold(
        onSuccess = {
            when (it) {
                is AccelerometerResult.SensorChanged -> it.model.toMessage() to Color.Black
                is AccelerometerResult.AccuracyChanged -> it.model.toMessage() to Color.DarkGray
            }
        },
        onFailure = {
            BaseUserBehaviorException.fromException(it).toMessage() to Color.Red
        }
    )
}

private fun formatTouchResult(result: Result<MotionEventModel>?): Pair<String, Color> {
    if (result == null) return "Touch: Idle" to Color.Black
    return result.fold(
        onSuccess = { it.toMessage() to Color.Black },
        onFailure = {
            BaseUserBehaviorException.fromException(it).toMessage() to Color.Red
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ComposeSampleScreenPreview() {
    SensorScreen(modifier = Modifier.padding(0.dp))
}
