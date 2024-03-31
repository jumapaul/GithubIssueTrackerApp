package com.example.githubtracker.domain.model

data class RepositoryDetails(
    val user: String,
    val repositoryName: String,
    val issues: List<String>,
    val labels: List<String>,
    val isPublic: Boolean
)
