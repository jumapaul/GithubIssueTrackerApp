package com.example.githubtracker.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubtracker.home.HomeScreen
import com.example.githubtracker.sign_in.presentation.SignInScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: String,
    activity: Activity
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(NavigationRoutes.SignInScreen.routes) {
            SignInScreen(navHostController, activity)
        }

        composable(NavigationRoutes.HomeScreen.routes) {
            HomeScreen()
        }
    }
}