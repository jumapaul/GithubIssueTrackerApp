package com.example.githubtracker.domain.use_cases

import UserRepositoryQuery
import android.util.Log
import com.example.githubtracker.common.Resource
import com.example.githubtracker.data.GitHubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val gitHubService: GitHubService
) {
    operator fun invoke(userQuery: String): Flow<Resource<List<UserRepositoryQuery.Node?>>?> =
        flow {
            try {
                val response =
                    Resource.Success(data = gitHubService.getRepositoriesByUserName(query = userQuery))

                emit(response.data)
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message ?: "An error occurred"))
            }
        }
}