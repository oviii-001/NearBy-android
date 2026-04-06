package com.ovi.nearby.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Map : Screen("map/{groupId}") {
        fun createRoute(groupId: String) = "map/"
    }
    object GroupDetails : Screen("group_details/{groupId}") {
        fun createRoute(groupId: String) = "group_details/"
    }
    object CreateGroup : Screen("create_group")
    object Settings : Screen("settings")
}
