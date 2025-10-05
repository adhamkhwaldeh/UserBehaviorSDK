package com.github.adhamkhwaldeh.userBehaviorSDK


import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import android.app.Activity
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager
import org.mockito.Mockito.*

class ActivityTouchManagerTest {

    private lateinit var activityTouchManager: ITouchManager
    private lateinit var mockActivity: Activity

    @Before
    fun setUp() {
        mockActivity = mock(Activity::class.java)
        activityTouchManager = UserBehaviorCoreSDK.Builder(mockActivity).build()
            .fetchOrCreateActivityTouchManager(mockActivity)
    }

    @Test
    fun testInitialization() {
        assertNotNull(activityTouchManager)
    }

    @Test
    fun testSomeFunctionality() {
        // Example: Replace with actual method and assertions
        // val result = activityTouchManager.someMethod()
        // assertEquals(expected, result)
    }
}
