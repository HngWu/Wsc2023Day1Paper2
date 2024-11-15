package com.example.wsc2023day1paper2app

import androidx.compose.material3.Button
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasClickAction
import kotlin.and

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import org.jetbrains.annotations.TestOnly
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.hasTestTag

class HomeScreenTest {
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


    fun isTab(): SemanticsMatcher {
        return SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Tab)
    }

    fun isButton(): SemanticsMatcher {
        return SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
    }

    val colorScheme = lightColorScheme(
        primary = Color(0xFFEF3840),
        onPrimary = Color.White,
        // Add other color customizations if needed
    )
    //        rule.setContent {
//            HomeScreen(
//                navController = rememberNavController(),
//                context = LocalContext.current,
//                materialColorTheme = colorScheme
//            )        }
    @Test
    fun TestUI() {
        rule.onNode(homeButton).assertExists()
        rule.onNode(readingLightButton).assertExists()
        rule.onNode(callAssistanceButton).assertExists()
        rule.onNode(shoppingButton).assertExists()

        rule.onNode(homeTab).assertExists()
        rule.onNode(readingLightTab).assertExists()
        rule.onNode(callAssistanceTab).assertExists()
        rule.onNode(shoppingTab).assertExists()
    }


    @Test
    fun TestReadingButton() {
        rule.onNode(readingLightButton).performClick()
        rule.onNode(readingLightOnOffButton).assertExists()



    }

}