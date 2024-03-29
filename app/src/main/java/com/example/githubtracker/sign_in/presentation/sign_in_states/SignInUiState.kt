package com.example.githubtracker.sign_in.presentation.sign_in_states

import com.example.githubtracker.sign_in.domain.model.UserData
//
data class SignInUiState(
    val data: UserData? = null,
    val errorMessage: String = ""
)
