package com.example.githubtracker.presentation.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val data: List<UserRepositoryQuery.Node?>? = emptyList(),
    val errorMessage: String = ""
)
