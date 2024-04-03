package com.example.githubtracker.domain

data class UserData(
    val accessToken: String,
    val name: String,
    val userName: String?,
    val profilePicture: String?
)
