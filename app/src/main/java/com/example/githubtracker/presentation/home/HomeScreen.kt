package com.example.githubtracker.presentation.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.githubtracker.common.composables.LoadImageComposables
import com.example.githubtracker.presentation.home.composables.RepositoryListingComposable
import com.example.githubtracker.presentation.navigation.NavigationRoutes
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    homeViewmodel: HomeViewmodel = hiltViewModel()
) {

    val context = LocalContext.current
    val repos = homeViewmodel.repoList.value
    var userName by remember {
        mutableStateOf("")
    }
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(context) {
        homeViewmodel.getUserInfo(context)
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (repos.data?.isEmpty() == true) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Blue,
                strokeWidth = 3.dp
            )
        }

        if (repos.data?.isNotEmpty() == true) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {

                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Welcome back ${homeViewmodel.user.value?.userName}")

                    Box(
                        modifier = modifier
                            .size(30.dp)
                            .clip(shape = CircleShape)
                    ) {
                        LoadImageComposables(url = homeViewmodel.user.value?.profilePicture.orEmpty())
                    }
                }

                Spacer(modifier = modifier.height(15.dp))

                OutlinedTextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                    },
                    shape = RoundedCornerShape(10.dp),
                    placeholder = {
                        Text(text = "Enter Username")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    modifier = modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            homeViewmodel.getRepositoryList("user:${userName.trim()} is:public sort:updated")
                        }
                    )
                )


                Spacer(modifier = modifier.height(15.dp))

                Text(
                    text = "List Of Repositories",
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start,
                ) {

                    items(repos.data) { item ->
                        val time = item?.repo?.onRepository?.createdAt.toString()
                        val localTime = ZonedDateTime.parse(time)
                        val formatter = DateTimeFormatter.ofPattern("EE dd/MM/yyy hh:mm:ss a")
                        val convertedTime = localTime.format(formatter)
                        RepositoryListingComposable(
                            repositoryName = item?.repo?.onRepository?.name.orEmpty(),
                            createdAt = convertedTime,
                            onclick = {
                                navController.navigate(NavigationRoutes.RepoDetailScreen.routes)
                            }
                        )
                    }
                }
            }

            if (repos.errorMessage.isNotBlank()) {
                Log.d("-------->", "HomeScreen: ${repos.errorMessage}")
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(
                        text = repos.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        textAlign = TextAlign.Center
                    )

                    IconButton(onClick = { isRefreshing = true }) {
                        Icon(
                            imageVector = Icons.Default.Refresh, contentDescription = null,
                        )
                    }

                    if (isRefreshing) {
                        homeViewmodel.repoList.value
                    }
                }
            }
        }
    }
}