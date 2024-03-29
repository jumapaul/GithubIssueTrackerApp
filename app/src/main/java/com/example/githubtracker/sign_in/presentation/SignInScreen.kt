package com.example.githubtracker.sign_in.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.githubtracker.R
import com.example.githubtracker.navigation.NavigationRoutes

@Composable
fun SignInScreen(
    navController: NavController,
    activity: Activity,
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = hiltViewModel()
) {

    val accessToken = signInViewModel.authState.collectAsState()


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        OutlinedButton(onClick = {
            signInViewModel.signInWithGithub(activity = activity)
        }, modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.padding(5.dp)
            ) {
                Text(text = "Sign In with github")
                Spacer(modifier = modifier.width(10.dp))
                Icon(painter = painterResource(id = R.drawable.github), contentDescription = null)
            }
        }
    }


    if (accessToken.value.data?.accessToken?.isNotEmpty() == true) {
        navController.navigate(NavigationRoutes.HomeScreen.routes)
    }
}