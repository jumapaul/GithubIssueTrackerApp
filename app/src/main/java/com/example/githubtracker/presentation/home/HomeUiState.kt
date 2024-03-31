package com.example.githubtracker.presentation.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: List<UserRepositoryQuery.Repo?>? = emptyList(),
    val errorMessage: String = ""
)
