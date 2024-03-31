package com.example.githubtracker.presentation.sign_in.sign_in_states

import com.example.githubtracker.domain.UserData
//
data class SignInUiState(
    val data: UserData? = null,
    val errorMessage: String = ""
)
