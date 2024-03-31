package com.example.githubtracker.domain.use_cases

import UserRepositoryQuery
import com.example.githubtracker.common.Resource
import com.example.githubtracker.data.GitHubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val gitHubService: GitHubService
) {
    operator fun invoke(userQuery: String): Flow<Resource<UserRepositoryQuery.Search?>?> = flow {
        val response =
            Resource.Success(data = gitHubService.getRepositoriesByUserName(query = userQuery))
        emit(response.data)
    }
}