package com.example.githubtracker.domain.model

data class RepositoryList(
    val user: String,
    val isPublic: Boolean,
    val repositoryName: String,
    val sorted: String = "updated"
)

enum class Visiblity {
    PRIVATE, PUBLIC
}
