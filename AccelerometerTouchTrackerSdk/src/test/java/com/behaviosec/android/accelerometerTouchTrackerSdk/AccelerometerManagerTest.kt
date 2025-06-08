package com.behaviosec.android.accelerometerTouchTrackerSdk

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AccelerometerManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class AccelerometerManagerTest {

    private lateinit var context: Context
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var listener: AccelerometerListener
    private lateinit var manager: AccelerometerManager

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        sensorManager = mock(SensorManager::class.java)
        sensor = mock(Sensor::class.java)
        listener = mock(AccelerometerListener::class.java)

        `when`(context.getSystemService(Context.SENSOR_SERVICE)).thenReturn(sensorManager)
        `when`(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)).thenReturn(sensor)

        manager = AccelerometerManager(context)
        manager.addListener(listener)
        manager.setEnabled(true)
    }

    @Test
    fun testOnSensorChanged_callsListener() {
        val event = mock(SensorEvent::class.java)
        manager.onSensorChanged(event)
        verify(listener).onSensorChanged(eq(event), any())
    }

    @Test
    fun testOnAccuracyChanged_callsListener() {
        manager.onAccuracyChanged(sensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
        verify(listener).onAccuracyChanged(eq(sensor), eq(SensorManager.SENSOR_STATUS_ACCURACY_HIGH), any())
    }
}