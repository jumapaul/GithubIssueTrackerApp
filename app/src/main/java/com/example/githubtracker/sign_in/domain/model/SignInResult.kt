package com.example.githubtracker.sign_in.domain.model

import android.net.Uri

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val accessToken: String,
    val userName: String?,
    val profilePicture: Uri?
)
