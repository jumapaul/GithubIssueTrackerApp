package com.example.githubtracker.presentation.navigation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubtracker.presentation.home.HomeScreen
import com.example.githubtracker.presentation.repos_detail.RepoDetailScreen
import com.example.githubtracker.presentation.sign_in.SignInScreen

@RequiresApi(Build.VERSION_CODES.O)
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
            HomeScreen(navHostController)
        }

        composable(NavigationRoutes.RepoDetailScreen.routes) {
            RepoDetailScreen()
        }
    }
}