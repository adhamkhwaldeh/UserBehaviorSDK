package com.behaviosec.android.accelerometerTouchTrackerSdk.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.behaviosec.android.accelerometerTouchTrackerSdk.R
import com.behaviosec.android.accelerometerTouchTrackerSdk.helpers.DateHelpers
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerErrorListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.logging.Logger
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.AccelerometerEventModel
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.AccuracyChangedModel
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.ManagerErrorModel
import com.behaviosec.android.accelerometerTouchTrackerSdk.config.TouchTrackerConfig

class AccelerometerManager(
    private val context: Context,
    config: TouchTrackerConfig = TouchTrackerConfig()
) : BaseManager(config), SensorEventListener {

    private var sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val activityManagersLock = Any()

    @Volatile
    private var accelerometerListener: AccelerometerListener? = null

    @Volatile
    private var errorListener: AccelerometerErrorListener? = null

    init {
        Logger.logLevel = config.logLevel
        isLoggingEnabled = config.isLoggingEnabled
    }

    fun addListener(listener: AccelerometerListener) {
        synchronized(activityManagersLock) {
            this.accelerometerListener = listener
        }
    }

    fun addErrorListener(listener: AccelerometerErrorListener) {
        synchronized(activityManagersLock) {
            this.errorListener = listener
        }
    }

    fun removeErrorListener() {
        synchronized(activityManagersLock) {
            this.errorListener = null
        }
    }

    fun start() {
        try {
            if (accelerometer == null) {
                errorListener?.onAccelerometerError(
                    ManagerErrorModel(
                        null,
                        context.getString(R.string.accelerometer_sensor_not_available)
                    )
                )
                return
            }
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        } catch (e: Exception) {
            errorListener?.onAccelerometerError(
                ManagerErrorModel(
                    e,
                    context.getString(R.string.failed_to_start_accelerometer, e.message ?: "")
                )
            )
            if (isLoggingEnabled && config.isDebugMode) {
                Logger.e(
                    context.getString(R.string.accelerometer_manager),
                    context.getString(R.string.error_starting_accelerometer),
                    e
                )
            }
        }
    }

    fun stop() {
        try {
            sensorManager.unregisterListener(this)
        } catch (e: Exception) {
            errorListener?.onAccelerometerError(
                ManagerErrorModel(
                    e,
                    context.getString(R.string.failed_to_stop_accelerometer, e.message ?: ""),
                )
            )
            if (isLoggingEnabled && config.isDebugMode) {
                Logger.e(
                    context.getString(R.string.accelerometer_manager),
                    context.getString(R.string.error_stopping_accelerometer),
                    e
                )
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        val listener = accelerometerListener
        if (config.isEnabled) {
            listener?.onSensorChanged(
                AccelerometerEventModel(
                    event,
                    DateHelpers.getCurrentDate()
                )
            )
        }
        if (isLoggingEnabled && config.isDebugMode) {
            Logger.d(
                context.getString(R.string.accelerometer),
                context.getString(
                    R.string.accelerometer_values,
                    event.values[0],
                    event.values[1],
                    event.values[2]
                )
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        accelerometerListener?.onAccuracyChanged(
            AccuracyChangedModel(
                sensor, accuracy, DateHelpers.getCurrentDate()
            )
        )
        if (isLoggingEnabled && config.isDebugMode) {
            Logger.d(
                context.getString(R.string.accelerometer),
                context.getString(R.string.accuracy_changed, accuracy)
            )
        }
    }

}
