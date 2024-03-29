package com.example.githubtracker.common.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun LoadImageComposables(
    url: String
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(url).build(),
        contentDescription = null,
        contentScale = ContentScale.FillBounds
    )
}