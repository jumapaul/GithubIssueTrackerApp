package com.example.githubtracker.navigation

sealed class NavigationRoutes(val routes: String) {
    object SignInScreen: NavigationRoutes("sign_in")
    object HomeScreen: NavigationRoutes("home")
}