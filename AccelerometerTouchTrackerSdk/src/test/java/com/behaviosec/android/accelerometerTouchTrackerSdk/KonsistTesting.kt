package com.behaviosec.android.accelerometerTouchTrackerSdk

import com.lemonappdev.konsist.api.ext.list.withNameContaining
import com.lemonappdev.konsist.api.verify.assertTrue
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.*
import com.lemonappdev.konsist.api.ext.list.packages
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withoutNameEndingWith
import org.junit.Assert
import org.junit.Test

class KonsistTesting {
    @Test
    fun `all interfaces with Listener in name should be in listeners package`() {
        val interfaces = Konsist.scopeFromProject()
            .interfaces()
            .withNameContaining("Listener")
            .filterNot { it.packagee?.hasNameEndingWith("listeners") == true }
        Assert.assertTrue(interfaces.isEmpty())
    }

    @Test
    fun `all classes with Helper in name should be in helpers package`() {
        val helpers = Konsist.scopeFromProject()
            .objects()
            .withNameContaining("Helper")
            .filterNot { it.packagee?.hasNameEndingWith("helpers") == true }
        Assert.assertTrue(helpers.isEmpty())
    }

    @Test
    fun `all classes with Manager in name should be in managers package`() {
        val managers = Konsist.scopeFromProject()
            .classes()
            .withNameContaining("Manager")
            .filterNot { it.packagee?.hasNameEndingWith("managers") == true }
        Assert.assertTrue(managers.isEmpty())
    }
}
