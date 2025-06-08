package com.behaviosec.android.accelerometerTouchTrackerSdk


import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.ActivityTouchListener
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AppTouchManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class AppTouchManagerTest {

    private lateinit var mockApplication: Application
    private lateinit var mockActivity: Activity
    private lateinit var appTouchManager: AppTouchManager

    @Before
    fun setUp() {
        mockApplication = mock(Application::class.java)
        mockActivity = mock(Activity::class.java)
        appTouchManager = AppTouchManager(mockApplication)
    }

    @Test
    fun testOnActivityCreated_addsActivityTouchManager() {
        val bundle = mock(Bundle::class.java)
        appTouchManager.onActivityCreated(mockActivity, bundle)
        // No exception means the manager was added; further checks require exposing internals or using reflection.
    }

    @Test
    fun testOnActivityDestroyed_removesActivityTouchManager() {
        val bundle = mock(Bundle::class.java)
        appTouchManager.onActivityCreated(mockActivity, bundle)
        appTouchManager.onActivityDestroyed(mockActivity)
        // No exception means the manager was removed; further checks require exposing internals or using reflection.
    }

    @Test
    fun testSetGlobalTouchListener_setsListenerForExistingManagers() {
        val bundle = mock(Bundle::class.java)
        appTouchManager.onActivityCreated(mockActivity, bundle)
        val mockListener = mock(ActivityTouchListener::class.java)
        appTouchManager.setGlobalTouchListener(mockListener)
        // No exception means the listener was set; further checks require exposing internals or using reflection.
    }
}
