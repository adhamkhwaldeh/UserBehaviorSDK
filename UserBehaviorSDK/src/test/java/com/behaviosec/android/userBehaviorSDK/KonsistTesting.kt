package com.behaviosec.android.userBehaviorSDK

import androidx.lifecycle.ViewModel
import com.lemonappdev.konsist.api.ext.list.withNameContaining
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withRepresentedTypeOf
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
            .withNameEndingWith("Helpers")
            .filterNot { it.packagee?.hasNameEndingWith("helpers") == true }
        Assert.assertTrue(helpers.isEmpty())
    }

    @Test
    fun `all classes with Manager in name should be in managers package`() {
        val managers = Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("Manager")
            .filterNot { it.packagee?.hasNameEndingWith("managers") == true }
        Assert.assertTrue(managers.isEmpty())
    }

    @Test
    fun `all classes with ViewModel in name should be in viewmodel package`() {
        val viewModels = Konsist.scopeFromProject()
            .classes()
            .withRepresentedTypeOf(ViewModel::class)
            .withNameEndingWith("ViewModel")
            .filterNot { it.packagee?.hasNameEndingWith("viewModels") == true }
        Assert.assertTrue(viewModels.isEmpty())
    }

    @Test
    fun `all classes with Model in name should be in model package`() {
        val models = Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("Model")
            .filter { it.name.endsWith("Model") && it.hasDataModifier }
            .filterNot { it.packagee?.hasNameEndingWith("models") == true }
        Assert.assertTrue(models.isEmpty())
    }

    @Test
    fun `all classes with Repository in name should be in repositories package`() {
        val repositories = Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("Repository")
            .filterNot { it.packagee?.hasNameEndingWith("repositories") == true }
        Assert.assertTrue(repositories.isEmpty())
    }
}
