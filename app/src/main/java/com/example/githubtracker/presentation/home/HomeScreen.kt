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
import androidx.compose.runtime.collectAsState
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
import com.example.githubtracker.common.composables.LoadImageComposables
import com.example.githubtracker.common.getUser
import com.example.githubtracker.presentation.home.composables.GetLabelListing
import com.example.githubtracker.presentation.home.composables.IssuesListingComposable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewmodel: HomeViewmodel = hiltViewModel()
) {

    val context = LocalContext.current
    val issues = homeViewmodel.homeUiState.collectAsState().value


    var issueName by remember {
        mutableStateOf("")
    }

    val user = getUser(context)?.userName

    val labels = listOf(
        "Bug",
        "Documentation",
        "Duplicate",
        "Enhancement",
        "Good first issue",
        "Help wanted",
        "Invalid",
        "Question",
        "Wontfix"

    )

    val selectedLabels = remember {
        mutableStateOf(emptyList<String>())
    }


    LaunchedEffect(context) {
        homeViewmodel.getUserInfo(context, user)
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (issues.data?.isEmpty() == true && issues.errorMessage.isBlank() && issueName.isNotEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Blue,
                strokeWidth = 3.dp
            )
        }
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
                Text(text = "Welcome back ${homeViewmodel.user.value?.name}")

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
                value = issueName,
                onValueChange = {
                    issueName = it
                    homeViewmodel.searchIssues(issueName)
                },
                maxLines = 1,
                shape = RoundedCornerShape(10.dp),
                placeholder = {
                    Text(text = "Enter issue name")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
            )


            Spacer(modifier = modifier.height(15.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

            }

            Text(
                text = "List Of Issues",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                GetLabelListing(labels = labels, selectedLabels = selectedLabels,
                    onCheck = {
                        homeViewmodel.searchLabel(selectedLabels.value)
                    }
                )

            }

            Spacer(modifier = modifier.height(10.dp))

            if (issues.data?.isNotEmpty() == true) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    items(issues.data) { item ->
                        IssuesListingComposable(
                            issueName = item,
                        )

                    }
                }
            } else if (issues.data?.isEmpty() == true && issueName.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No results found")
                }
            } else if (issues.data?.isEmpty() == true && selectedLabels.value.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No results found")
                }
            }

        }

        if (issues.errorMessage.isNotBlank()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    text = issues.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

