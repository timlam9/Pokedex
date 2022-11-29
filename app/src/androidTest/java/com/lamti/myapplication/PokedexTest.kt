package com.lamti.myapplication

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class PokedexTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigateToDetailsScreen() {
        rule.waitUntil { rule.onAllNodesWithText("Bulbasaur").fetchSemanticsNodes().isNotEmpty() }

        rule.onNodeWithText("Bulbasaur").performClick()
        rule.waitUntil { rule.onAllNodesWithText("Bulbasaur").fetchSemanticsNodes().isNotEmpty() }

        rule.onNodeWithText("Base Stats").assertExists()
    }
}