package com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.github.adhamkhwaldeh.userBehaviorSDK.R
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.Logger
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.BaseManager
import com.github.adhamkhwaldeh.userBehaviorSDK.repositories.HelpersRepository

/**
 * Accelerometer manager
 *
 * @property context
 * @constructor
 *
 * @param config
 */
internal class AccelerometerManager private constructor(
    private val context: Context,
    private val helpersRepository: HelpersRepository,
    logger: Logger,
    config: AccelerometerConfig = AccelerometerConfig(),
) : BaseManager<AccelerometerListener, AccelerometerErrorListener,
        AccelerometerConfig>(config, logger), IAccelerometerManager {

    companion object {
        @JvmSynthetic
        internal fun create(
            context: Context,
            helpersRepository: HelpersRepository,
            logger: Logger,
            config: AccelerometerConfig = AccelerometerConfig(),
        ): AccelerometerManager = AccelerometerManager(
            context = context,
            helpersRepository = helpersRepository,
            logger = logger,
            config = config
        )
    }

    private val sensorManager: SensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val accelerometer: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

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
            val model = AccuracyChangedModel(
                sensor, accuracy, helpersRepository.getCurrentDate()
            )
            for (listener in listeners) {
                listener.onAccuracyChanged(model)
            }
            if (config.isLoggingEnabled && config.isDebugMode) {
                logger.d(
                    context.getString(R.string.accelerometer),
                    context.getString(R.string.accuracy_changed, accuracy),
                    config,
                )
            }
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