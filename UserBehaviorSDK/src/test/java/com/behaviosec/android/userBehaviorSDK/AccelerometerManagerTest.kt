package com.github.adhamkhwaldeh.userBehaviorSDK

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.AccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.repositories.HelpersRepository
import com.github.adhamkhwaldeh.userBehaviorSDK.repositories.MockedRepository
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
        val helpersRepository = mock(HelpersRepository::class.java)

        `when`(context.getSystemService(Context.SENSOR_SERVICE)).thenReturn(sensorManager)
        `when`(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)).thenReturn(sensor)
        `when`(helpersRepository.getCurrentDate()).thenReturn(MockedRepository.mockDate)

        manager = AccelerometerManager(context,helpersRepository)
        manager.addListener(listener)
        manager.setEnabled(true)
    }

    @Test
    fun testOnSensorChanged_callsListener() {
        val event = mock(SensorEvent::class.java)
        event.accuracy = MockedRepository.accuracy
//        manager.onSensorChanged(event)
        verify(listener).onSensorChanged(AccelerometerEventModel(event, MockedRepository.mockDate))
    }

    @Test
    fun testOnAccuracyChanged_callsListener() {
        val mockedAccuracy = MockedRepository.getMockedAccuracy(sensor)
//        manager.onAccuracyChanged(sensor, mockedAccuracy.accuracy)
        verify(listener).onAccuracyChanged(mockedAccuracy)
    }
}