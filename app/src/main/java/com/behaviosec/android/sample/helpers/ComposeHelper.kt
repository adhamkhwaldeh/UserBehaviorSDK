package com.behaviosec.android.sample.helpers

import android.view.View
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import com.github.adhamkhwaldeh.userBehaviorSDKKtx.AccelerometerResult

fun formatAccelerometerResult(result: Result<AccelerometerResult>?): Pair<String, Color> {
    if (result == null) return "ACC: Idle" to Color.Black
    return result.fold(
        onSuccess = {
            when (it) {
                is AccelerometerResult.SensorChanged -> it.model.toMessage() to Color.Black
                is AccelerometerResult.AccuracyChanged -> it.model.toMessage() to Color.DarkGray
            }
        },
        onFailure = {
            BaseSDKException.fromException(it).toMessage() to Color.Red
        }
    )
}

fun formatTouchResult(result: Result<MotionEventModel>?): Pair<String, Color> {
    if (result == null) return "Touch: Idle" to Color.Black
    return result.fold(
        onSuccess = { it.toMessage() to Color.Black },
        onFailure = {
            BaseSDKException.fromException(it).toMessage() to Color.Red
        }
    )
}

fun Modifier.fetchView(
    enabled: Boolean = true,
    onViewFetched: (View) -> Unit
): Modifier = composed {
    if (enabled) {
        val view = LocalView.current
        onViewFetched(view)
    }
    this
}