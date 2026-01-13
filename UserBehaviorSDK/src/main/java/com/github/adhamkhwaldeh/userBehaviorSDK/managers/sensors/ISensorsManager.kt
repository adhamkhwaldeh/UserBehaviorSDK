package com.github.adhamkhwaldeh.userBehaviorSDK.managers.sensors

import com.github.adhamkhwaldeh.commonsdk.managers.BaseManager
import com.github.adhamkhwaldeh.userBehaviorSDK.config.SensorConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.SensorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.SensorErrorListener

/**
 * Interface for the Sensor Manager, responsible for collecting gyroscope sensor data.
 */
interface ISensorsManager : BaseManager<SensorListener, SensorErrorListener, SensorConfig>