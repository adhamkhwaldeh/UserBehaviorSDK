package com.behaviosec.android.accelerometerTouchTrackerSdk.repositories

import android.hardware.Sensor
import android.hardware.SensorManager
import com.behaviosec.android.accelerometerTouchTrackerSdk.models.AccuracyChangedModel
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
