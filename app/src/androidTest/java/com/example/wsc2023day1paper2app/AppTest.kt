package com.example.wsc2023day1paper2app

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import android.media.MediaPlayer
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import kotlinx.coroutines.delay




class AppTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    val homeButton = hasText("Home") and hasClickAction() and isButton()
    val readingLightButton = hasText("Reading Light") and hasClickAction() and isButton()
    val callAssistanceButton = hasText("Call Assistance") and hasClickAction() and isButton()
    val shoppingButton = hasText("Shopping") and hasClickAction() and isButton()

    val homeTab = hasText("Home") and isTab()
    val readingLightTab = hasText("Reading Light") and isTab()
    val callAssistanceTab = hasText("Call Assistance") and isTab()
    val shoppingTab = hasText("Shopping") and isTab()
    val readingLightOnOffButton = hasTestTag("readingLightOnOffButton") and hasClickAction() and isButton()
    val callButton = hasTestTag("callButton") and hasClickAction() and isButton()

    val mainTab = hasText("mains") and isTab()
    @Test
    fun TC1() {
        rule.onNode(homeButton).assertExists()
        rule.onNode(readingLightButton).assertExists()
        rule.onNode(callAssistanceButton).assertExists()
        rule.onNode(shoppingButton).assertExists()

        rule.onNode(homeTab).assertExists()
        rule.onNode(readingLightTab).assertExists()
        rule.onNode(callAssistanceTab).assertExists()
        rule.onNode(shoppingTab).assertExists()

        rule.onNode(readingLightButton).performClick()
        rule.onNode(readingLightOnOffButton).assertExists()
    }


    @Test
    fun TC2AndTC3() {
        rule.onNode(readingLightButton).performClick()
        rule.onNode(readingLightOnOffButton).performClick()

        rule.onNode(readingLightOnOffButton).assertTextEquals("On")

        rule.onNode(readingLightOnOffButton).performClick()
        rule.onNode(readingLightOnOffButton).assertTextEquals("Off")

    }


    @Test
    fun TC4() {
        val testMediaPlayer = TestMediaPlayer()
        val expectedColor = Color.Blue


        rule.onNode(callAssistanceButton).performClick()
        rule.onNode(callButton).assertExists()


        // Perform click and verify button exists
        rule.onNode(callAssistanceButton).performClick()
        rule.onNode(callButton).assertExists()

        // Verify button is disabled and blinks for 5 seconds
        rule.onNode(callButton).assertIsNotEnabled()

        rule.onNodeWithContentDescription("Blue button").assertExists()

// Verify sound is played

// Wait until the button is enabled
        rule.waitUntil(timeoutMillis = 5000) {
            rule.onNode(callButton).fetchSemanticsNode().config.getOrNull(SemanticsProperties.Disabled) == null
        }

        rule.onNode(callButton).assertIsEnabled()
    }


    @Test
    fun TC5AndTC6AndTC7() {
        rule.onNode(shoppingButton).performClick()
        // Verify default filter is "mains"
        rule.onNodeWithTag("mains").assertExists().assertIsSelected()

//        // Verify first and last items of the main dish are displayed
        val firstMainItem = "Chicken Caesar Wrap" // Replace with actual first item name
        val lastMainItem = "Cheese Pizza" // Replace with actual last item name

        rule.waitUntil(timeoutMillis = 5000) {
            rule.onNodeWithTag("mains").fetchSemanticsNode().config.getOrNull(SemanticsProperties.Disabled) == null
        }

        rule.onNodeWithTag(firstMainItem).assertExists()
        rule.onNodeWithTag(lastMainItem).assertExists()



        rule.onNodeWithTag("drinks").performClick()
        val firstDrinkItem = "Coca-Cola" // Replace with actual first item name
        val lastDrinkItem = "Hot Chocolate" // Replace with actual last item name

        rule.onNodeWithTag(firstDrinkItem).assertExists()

        rule.onNodeWithTag(("productList")).performScrollToIndex(10)
        rule.onNodeWithTag(lastDrinkItem).assertExists()



        rule.onNodeWithTag("mains").performClick()
        rule.onNodeWithTag("Quinoa Bowl").performClick()
        rule.onNodeWithTag("12.99").assertExists()
        rule.onNodeWithTag("Quinoa, black beans, avocado, corn, and salsa.").assertExists()

    }




    fun isTab(): SemanticsMatcher {
        return SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)
    }

    fun isButton(): SemanticsMatcher {
        return SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
    }
    class TestMediaPlayer : MediaPlayer() {
        var isSoundPlayed = false

        override fun start() {
            super.start()
            isSoundPlayed = true
        }
    }
}