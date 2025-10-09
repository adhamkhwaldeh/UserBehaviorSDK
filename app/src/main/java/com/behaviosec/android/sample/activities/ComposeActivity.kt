package com.behaviosec.android.sample.activities

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.data.position
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.behaviosec.android.sample.R
import com.behaviosec.android.sample.helpers.fetchView
import com.behaviosec.android.sample.helpers.formatAccelerometerResult
import com.behaviosec.android.sample.helpers.formatTouchResult
import com.behaviosec.android.sample.ui.DisplayButtons
import com.behaviosec.android.sample.viewModels.CoroutineViewModel
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.ProvideUserBehaviorSDK
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.collectViewTouchEvents
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.rememberAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.rememberActivityTouchManager
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.AccelerometerResult
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.accelerometerResultFlow
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.touchResultFlow
import org.koin.android.ext.android.get

class ComposeActivity : ComponentActivity() {

    private val coroutineViewModel: CoroutineViewModel by lazy { CoroutineViewModel(get()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeScreen(coroutineViewModel)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeScreen(coroutineViewModel: CoroutineViewModel) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Compose Sample (SDK Extensions)") }) }
    ) { padding ->
        UserBehaviorExperimentalScreen(
            coroutineViewModel,
            modifier = Modifier.padding(padding)
        )
    }
}

//@OptIn(ExperimentalUserBehaviorSDKComposeApi::class)
@Composable
fun UserBehaviorExperimentalScreen(
    viewModel: CoroutineViewModel,
    modifier: Modifier = Modifier
) {

    // 1. Get the context from the composition local
    val context = LocalContext.current

    // 2. Cast the context to an Activity
    // Using 'remember' ensures this isn't re-calculated on every recomposition
    val activity = remember { context as? Activity }


    val scrollState = rememberScrollState()

    // --- State Management within the Composable ---
    val accelerometerData by viewModel.accelerometerResult.collectAsState()
    val activityTouchData by viewModel.activityTouchResult.collectAsState()
    val viewTouchData by viewModel.viewTouchResult.collectAsState()

    // 1. Create a state variable to control the collection
    var isViewTouchEnabled by remember { mutableStateOf(false) }


    // --- UI Layout ---
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
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
            viewModel.startTracking()
        }, onStopClick = {
            viewModel.stopTracking()
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
            activity?.let {
                viewModel.startActivityTouchTracking(activity)
            }
        }, onStopClick = {
            viewModel.stopActivityTouchTracking()
        })
        val (activityMsg, activityColor) = formatTouchResult(activityTouchData)
        Text(text = activityMsg, color = activityColor)


        // --- Touch Green View Section ---
        Text(
            text = "Touch Green View",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enable touch collection on the green box:", modifier = Modifier.weight(1f))
            Switch(
                checked = isViewTouchEnabled,
                onCheckedChange = { isViewTouchEnabled = it }
            )
        }
        val (viewMsg, viewColor) = formatTouchResult(viewTouchData)
        Text(text = viewMsg, color = viewColor)


        TouchableView(viewModel, isViewTouchEnabled)


    }
}

@Composable
fun TouchableView(
    viewModel: CoroutineViewModel,
    isViewTouchEnabled: Boolean,
) {
//    // 1. Get the host View for the Box.
//    val view = LocalView.current
//
//    // 2. Use LaunchedEffect to safely manage the lifecycle of the touch tracker.
//    // This block will run ONLY when 'isViewTouchEnabled' changes.
//    DisposableEffect(isViewTouchEnabled, view) {
//        if (isViewTouchEnabled) {
//            // This is called when isViewTouchEnabled becomes true.
//            viewModel.startViewTouchTracking(view)
//        } else {
//            // This is called when isViewTouchEnabled becomes false.
//            viewModel.stopViewTouchTracking()
//        }
//
//        // onDispose is a cleanup block that runs when the effect leaves
//        // the composition (e.g., screen is closed) or when the key changes.
//        // It ensures the tracker is always stopped.
//        onDispose {
//            viewModel.stopViewTouchTracking()
//        }
//    }

    val touchModifier = if (isViewTouchEnabled) {
        Modifier.fetchView { result ->
            // Pass the touch event data directly to the ViewModel
            viewModel.startViewTouchTracking(result)
        }
    } else {
        Modifier // Use an empty modifier when disabled
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 8.dp)
//            .fetchView( isViewTouchEnabled,{
//                if (isViewTouchEnabled) {
//                    // This is called when isViewTouchEnabled becomes true.
//                    viewModel.startViewTouchTracking(view)
//                } else {
//                    // This is called when isViewTouchEnabled becomes false.
//                    viewModel.stopViewTouchTracking()
//                }
//            })
            .pointerInput(Unit) { // The 'key' (Unit) determines when the listener is reset.
                awaitPointerEventScope {
//                    while (true) {
                        val event = awaitPointerEvent() // This suspends until a touch event occurs.

                        // 'event' is an AwaitPointerEvent. You can access its type, position, etc.
                        // For example, to get the primary touch position:
                        val position = event.changes.first().position
                        Log.d("BoxTouch", "Event type: ${event.type}, Position: $position")

                        // HERE you would call your ViewModel with the relevant data.
                        // You would need to adapt your ViewModel to accept this new kind of data,
                        // or convert this event into the MotionEventModel your manager expects.
//                    }
                }
            }
            .clickable {
                // This lambda is executed when the Box is clicked.
                Log.d("BoxClick", "The green box was clicked!")
                // You can call your ViewModel function here.
                // viewModel.onBoxClicked()
            }
            .background(Color(0xFF388E3C))
            .then(touchModifier)
        // holo_green_dark
    )
}

@Preview(showBackground = true)
@Composable
fun ComposeScreenPreview() {
    // A dummy context is required for the SDK builder.
    val dummyContext = object : Application() {}
    // Previews will crash if the CompositionLocal is not provided.
    // We provide a dummy instance of the SDK for rendering purposes.
    val dummySDK = UserBehaviorCoreSDK.Builder(dummyContext).build()

    ProvideUserBehaviorSDK(sdk = dummySDK) {
        ComposeSampleScreen()
    }
}
