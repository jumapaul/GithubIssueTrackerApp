package com.example.githubtracker.domain.model

data class RepositoryDetails(
    val repositoryName: String,
    val issues: UserRepositoryQuery.Issues
)
