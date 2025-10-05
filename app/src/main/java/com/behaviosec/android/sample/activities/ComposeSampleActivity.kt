package com.behaviosec.android.sample.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDKCompose.ProvideUserBehaviorSDK
import org.koin.android.ext.android.get
import com.behaviosec.android.sample.R

class ComposeSampleActivity : ComponentActivity() {

    private val userBehaviorCoreSDK: UserBehaviorCoreSDK by lazy { get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
        topBar = {
            TopAppBar(title = { Text("Compose Sample") })
        }
    ) { //padding ->
        SensorScreen(

        )
    }
}

@Composable
fun SensorScreen(
    onStartAccelerometer: () -> Unit = {},
    onStopAccelerometer: () -> Unit = {},
    onStartTouch: () -> Unit = {},
    onStopTouch: () -> Unit = {},
    onStartTouchView: () -> Unit = {},
    onStopTouchView: () -> Unit = {},
    accelerometerAccuracy: String = "",
    accelerometerSensor: String = "",
    touchDetails: String = "",
    touchViewDetails: String = ""
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.field_margin))
            .verticalScroll(scrollState)
    ) {

        // Accelerometer Section
        Text(
            text = "Accelerometer",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.field_margin))
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onStartAccelerometer,
                modifier = Modifier.weight(1f)
            ) {
                Text("Start")
            }

            Button(
                onClick = onStopAccelerometer,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.weight(1f)
            ) {
                Text("Stop")
            }
        }

        Text(
            text = accelerometerAccuracy,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.field_margin))
        )

        Text(
            text = accelerometerSensor,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.field_margin))
        )

        // Touch Activity Section
        Text(
            text = "Touch Activity",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.field_margin))
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onStartTouch,
                modifier = Modifier.weight(1f)
            ) {
                Text("Start")
            }

            Button(
                onClick = onStopTouch,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.weight(1f)
            ) {
                Text("Stop")
            }
        }

        Text(
            text = touchDetails,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.field_margin))
        )

        // Touch Green View Section
        Text(
            text = "Touch Green View",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.field_margin))
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onStartTouchView,
                modifier = Modifier.weight(1f)
            ) {
                Text("Start")
            }

            Button(
                onClick = onStopTouchView,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.weight(1f)
            ) {
                Text("Stop")
            }
        }

        Text(
            text = touchViewDetails,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.field_margin))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF388E3C)) // holo_green_dark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeSampleScreenPreview() {
    ComposeSampleScreen()
}