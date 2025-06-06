package com.behaviosec.android.gesturedetectorsdk

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent

class GestureDetectorBuilder {
    private var isEnabled: Boolean = true
    private var isDebugMode: Boolean = false
    private var isLoggingEnabled: Boolean = false

    fun setEnabled(enabled: Boolean): GestureDetectorBuilder {
        this.isEnabled = enabled
        return this
    }

    fun setDebugMode(debugMode: Boolean): GestureDetectorBuilder {
        this.isDebugMode = debugMode
        return this
    }

    fun setLoggingEnabled(loggingEnabled: Boolean): GestureDetectorBuilder {
        this.isLoggingEnabled = loggingEnabled
        return this
    }

    fun build(context: Context): GestureDetector {
        return GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {


            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (isEnabled) {
                    // Handle single tap
                    if (isLoggingEnabled) {
                        // Log the event
                    }
                }
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                if (isEnabled) {
                    // Handle double tap
                    if (isLoggingEnabled) {
                        // Log the event
                    }
                }
                return true
            }
            // Add more gesture handling methods as needed
        })
    }

}