package com.github.adhamkhwaldeh.userBehaviorSDK.managers.sensors

import com.github.adhamkhwaldeh.userBehaviorSDK.config.SensorConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.SensorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.SensorErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.IBaseManager

/**
 * Interface for the Sensor Manager, responsible for collecting gyroscope sensor data.
 */
interface ISensorsManager : IBaseManager<SensorListener, SensorErrorListener, SensorConfig>