package com.example.githubtracker.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubtracker.common.composables.LoadImageComposables

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewmodel: HomeViewmodel = hiltViewModel()
) {

    val context = LocalContext.current
    val userData = homeViewmodel.getUserInfo(context)

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(10.dp)
    ) {

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Welcome back ${userData?.userName.orEmpty()}")

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Box(
                    modifier = modifier
                        .size(50.dp)
                        .clip(shape = CircleShape)
                ) {
                    LoadImageComposables(url = userData?.profilePicture.orEmpty())
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(
    homeViewmodel: HomeViewmodel = hiltViewModel()
) {
    HomeScreen()
}