package com.behaviosec.android.sample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DisplayButtons(
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
