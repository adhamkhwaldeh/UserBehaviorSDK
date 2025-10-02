package com.behaviosec.android.userBehaviorSDK.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.behaviosec.android.userBehaviorSDK.R
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.behaviosec.android.userBehaviorSDK.logging.Logger
import com.behaviosec.android.userBehaviorSDK.models.AccelerometerEventModel
import com.behaviosec.android.userBehaviorSDK.models.AccuracyChangedModel
import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel
import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.TouchListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.behaviosec.android.userBehaviorSDK.managers.base.IBaseManager
import com.behaviosec.android.userBehaviorSDK.repositories.HelpersRepository
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Accelerometer manager
 *
 * @property context
 * @constructor
 *
 * @param config
 */
class AccelerometerManager(
    private val context: Context,
    private val helpersRepository: HelpersRepository,
    override val config: TouchTrackerConfig = TouchTrackerConfig(),
) : IBaseManager<AccelerometerListener, AccelerometerErrorListener> {

    override val listeners: CopyOnWriteArrayList<AccelerometerListener> =
        CopyOnWriteArrayList<AccelerometerListener>()

    override val errorListeners: CopyOnWriteArrayList<AccelerometerErrorListener> =
        CopyOnWriteArrayList<AccelerometerErrorListener>()

    private val sensorManager: SensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private var accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (config.isEnabled) {
                val model = AccelerometerEventModel(
                    event,
                    helpersRepository.getCurrentDate()
                )
                for (listener in listeners) {
                    listener.onSensorChanged(model)
                }
            }
            if (config.isLoggingEnabled && config.isDebugMode && event != null) {
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
            val model = AccuracyChangedModel(
                sensor, accuracy, helpersRepository.getCurrentDate()
            )
            for (listener in listeners) {
                listener.onAccuracyChanged(model)
            }
            if (config.isLoggingEnabled && config.isDebugMode) {
                Logger.d(
                    context.getString(R.string.accelerometer),
                    context.getString(R.string.accuracy_changed, accuracy)
                )
            }
        }
    }

    init {
        Logger.logLevel = config.logLevel
    }


    //#region Base Manager actions
    /**
     * Start
     *
     */
    override fun start() {
        synchronized(this) {
            try {
                if (accelerometer == null) {
                    notifyErrorListeners(
                        ManagerErrorModel(
                            null,
                            context.getString(R.string.accelerometer_sensor_not_available)
                        )
                    )
                    return
                }
                sensorManager.registerListener(
                    sensorEventListener,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_UI
                )
            } catch (e: Exception) {
                notifyErrorListeners(
                    ManagerErrorModel(
                        e,
                        context.getString(R.string.failed_to_start_accelerometer, e.message ?: "")
                    )
                )
//                if (isLoggingEnabled && config.isDebugMode) {
//                    Logger.e(
//                        context.getString(R.string.accelerometer_manager),
//                        context.getString(R.string.error_stopping_accelerometer),
//                        e
//                    )
//                }
            }
        }
    }

    /**
     * Stop
     *
     */
    override fun stop() {
        synchronized(this) {
            try {
                sensorManager.unregisterListener(sensorEventListener)
            } catch (e: Exception) {
                notifyErrorListeners(
                    ManagerErrorModel(
                        e,
                        context.getString(R.string.failed_to_stop_accelerometer, e.message ?: ""),
                    )
                )
                if (config.isLoggingEnabled && config.isDebugMode) {
                    Logger.e(
                        context.getString(R.string.accelerometer_manager),
                        context.getString(R.string.error_stopping_accelerometer),
                        e
                    )
                }
            }
        }
    }

    /**
     * Pause
     *
     */
    override fun pause() {
        stop()
    }

    /**
     * Resume
     *
     */
    override fun resume() {
        start()
    }
    //#endregion

}
