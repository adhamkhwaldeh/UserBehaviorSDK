package com.github.adhamkhwaldeh.userBehaviorSDK.managers.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.github.adhamkhwaldeh.userBehaviorSDK.R
import com.github.adhamkhwaldeh.userBehaviorSDK.config.SensorConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.SensorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.SensorErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.Logger
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.BaseManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorAccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.SensorEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.repositories.HelpersRepository
import java.util.Date

/**
 * An abstract base class for sensor-based managers (e.g., Accelerometer, Gyroscope).
 * It handles the common logic for sensor registration, state management, and event listening.
 *
 * @param C The type of the callback listener.
 * @param E The type of the error listener.
 * @param F The type of the configuration.
 * @property context The application context.
 * @property helpersRepository A repository providing helper functions.
 * @property logger The logger instance for logging messages.
 * @property sensorType The integer constant for the sensor type (e.g., `Sensor.TYPE_ACCELEROMETER`).
 */
internal class SensorsManager private constructor(
    private val context: Context,
    private val helpersRepository: HelpersRepository,
    private val sensorType: Int,
    logger: Logger,
    config: SensorConfig,
) : BaseManager<SensorListener, SensorErrorListener,
        SensorConfig>(config, logger), ISensorsManager {

    companion object {
        @JvmSynthetic
        internal fun create(
            context: Context,
            helpersRepository: HelpersRepository,
            sensorType: Int,
            logger: Logger,
            config: SensorConfig = SensorConfig(),
        ): SensorsManager = SensorsManager(
            context = context,
            helpersRepository = helpersRepository,
            sensorType = sensorType,
            logger = logger,
            config = config
        )
    }

    private val sensorManager: SensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val sensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(sensorType)
    }

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (config.isEnabled) {
                val model = SensorEventModel(
                    event,
                    helpersRepository.getCurrentDate()
                )
                for (listener in listeners) {
                    listener.onSensorChanged(model)
                }
            }
            if (event != null) {
                logger.d(
                    context.getString(R.string.accelerometer),
                    context.getString(
                        R.string.accelerometer_values,
                        event.values[0],
                        event.values[1],
                        event.values[2]
                    ),
                    config
                )
            }

        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            val model = SensorAccuracyChangedModel(
                sensor, accuracy, helpersRepository.getCurrentDate()
            )
            for (listener in listeners) {
                listener.onAccuracyChanged(model)
            }
            logger.d(
                context.getString(R.string.accelerometer),
                context.getString(R.string.accuracy_changed, accuracy),
                config,
            )
        }
    }


    //#region Base Manager actions
    var isManagerStarted = false

    override fun isStarted(): Boolean {
        return isManagerStarted
    }

    /**
     * Start
     *
     */
    override fun start() {
        synchronized(this) {
            try {
                if (sensor == null) {
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
                    sensor,
                    SensorManager.SENSOR_DELAY_UI
                )
                isManagerStarted = true
            } catch (e: Exception) {
                notifyErrorListeners(
                    ManagerErrorModel(
                        e,
                        context.getString(R.string.failed_to_start_accelerometer, e.message ?: "")
                    )
                )
                logger.e(
                    context.getString(R.string.accelerometer_manager),
                    context.getString(R.string.error_stopping_accelerometer),
                    config = config,
                    e
                )
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
                isManagerStarted = false
            } catch (e: Exception) {
                notifyErrorListeners(
                    ManagerErrorModel(
                        e,
                        context.getString(R.string.failed_to_stop_accelerometer, e.message ?: ""),
                    )
                )
                logger.e(
                    context.getString(R.string.accelerometer_manager),
                    context.getString(R.string.error_stopping_accelerometer),
                    config = config,
                    e
                )
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