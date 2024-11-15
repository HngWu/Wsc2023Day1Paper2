package com.example.wsc2023day1paper2app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test

class LaunchScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verifyLaunchIcon() {
        // Get the application context
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Get the actual launcher icon resource ID
        val actualLauncherIconId = context.applicationInfo.icon

        // Get the expected ic_launcher resource ID
        val expectedLauncherIconId = context.resources.getIdentifier(
            "ic_launcher_round",
            "mipmap",
            context.packageName
        )

        // Assert that the actual launcher icon matches the expected ic_launcher
        assert(actualLauncherIconId == expectedLauncherIconId) {
            "Launch icon does not match ic_launcher resource"
        }
    }

}