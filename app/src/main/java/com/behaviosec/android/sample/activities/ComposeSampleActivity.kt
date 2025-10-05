package com.behaviosec.android.sample.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class ComposeSampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleScreen()
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
    ) { padding ->
        Button(
            onClick = {},
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            Text("Hello from Compose!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeSampleScreenPreview() {
    ComposeSampleScreen()
}