package com.github.adhamkhwaldeh.userBehaviorSDK.models

import android.app.Activity
import android.app.Application
import android.hardware.Sensor
import android.view.View

sealed interface ManagerKey
object ManagerAccelerometerKey : ManagerKey

//#region Touch Keys
sealed interface ManagerTouchKey : ManagerKey
data class ManagerActivityKey(val activity: Activity) : ManagerTouchKey
data class ManagerViewKey(val view: View) : ManagerTouchKey
data class ManagerApplicationKey(val application: Application) : ManagerTouchKey
//#endregion

//#region Sensor Keys
sealed interface ManagerSensorKey : ManagerKey {
    val sensorType: Int
}

// --- MOTION SENSORS ---


object ManagerAccelerometerSensorKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_ACCELEROMETER
}

object ManagerGyroscopeKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_GYROSCOPE
}

object ManagerGravityKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_GRAVITY
}

object ManagerLinearAccelerationKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_LINEAR_ACCELERATION
}

object ManagerRotationVectorKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_ROTATION_VECTOR
}

object ManagerStepCounterKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_STEP_COUNTER
}

object ManagerStepDetectorKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_STEP_DETECTOR
}

object ManagerGameRotationVectorKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_GAME_ROTATION_VECTOR
}

object ManagerGeomagneticRotationVectorKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR
}

// --- POSITION SENSORS ---
object ManagerMagnetometerKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_MAGNETIC_FIELD
}

object ManagerProximityKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_PROXIMITY
}

// --- ENVIRONMENT SENSORS ---
object ManagerLightKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_LIGHT
}

object ManagerPressureKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_PRESSURE
}

object ManagerRelativeHumidityKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_RELATIVE_HUMIDITY
}

object ManagerAmbientTemperatureKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_AMBIENT_TEMPERATURE
}

// --- OTHER SENSORS ---
object ManagerHeartRateKey : ManagerSensorKey {
    override val sensorType: Int = Sensor.TYPE_HEART_RATE
}

object ManagerHingeAngleKey : ManagerSensorKey { // For foldables
    override val sensorType: Int = Sensor.TYPE_HINGE_ANGLE
}

//#endregion