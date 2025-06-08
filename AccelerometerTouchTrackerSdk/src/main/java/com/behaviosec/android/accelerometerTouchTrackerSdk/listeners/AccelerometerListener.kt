package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import android.hardware.Sensor
import android.hardware.SensorEvent
import java.util.Date

interface AccelerometerListener {
    fun onSensorChanged(event: SensorEvent, date: Date) {}
    fun onAccuracyChanged(sensor: Sensor?, accuracy: Int, date: Date) {}
}