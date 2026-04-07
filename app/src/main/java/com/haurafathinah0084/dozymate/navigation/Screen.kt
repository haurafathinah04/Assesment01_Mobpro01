package com.haurafathinah0084.dozymate.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
}