package com.behaviosec.android.accelerometerTouchTrackerSdk.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.behaviosec.android.accelerometerTouchTrackerSdk.helpers.DateHelpers
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerListener

class AccelerometerManager(
    context: Context
) : BaseManager(), SensorEventListener {

    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var accelerometerListener: AccelerometerListener? = null


    fun addListener(listener: AccelerometerListener) {
        this.accelerometerListener = listener
    }


    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (isEnabled) {
            accelerometerListener?.onSensorChanged(event, DateHelpers.getCurrentDate())
        }
        if (isLoggingEnabled && isDebugMode) {
            //        val x = event.values[0]
//        val y = event.values[1]
//        val z = event.values[2]
//        Log.d("Accelerometer", "x: $x, y: $y, z: $z")
            Log.d(
                "Accelerometer",
                "x: ${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}"
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        accelerometerListener?.onAccuracyChanged(sensor, accuracy, DateHelpers.getCurrentDate())
        if (isLoggingEnabled && isDebugMode) {
            Log.d("Accelerometer", "Accuracy changed: $accuracy")
        }
    }

}