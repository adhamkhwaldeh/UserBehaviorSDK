package com.github.adhamkhwaldeh.userBehaviorSDK.repositories

import android.hardware.Sensor
import android.hardware.SensorManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import java.util.Date

class MockedRepository {
    companion object {

        val mockDate: Date = Date(System.currentTimeMillis())
        val accuracy = SensorManager.SENSOR_STATUS_ACCURACY_HIGH

        fun getMockedAccuracy(sensor: Sensor): AccuracyChangedModel {
            return AccuracyChangedModel(
                sensor = sensor,
                accuracy = accuracy,
                date = mockDate,//DateHelpers.getCurrentDate(),
            )
        }

    }
}
