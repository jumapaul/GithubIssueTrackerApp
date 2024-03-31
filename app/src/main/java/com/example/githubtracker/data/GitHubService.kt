package com.example.githubtracker.data

import UserRepositoryQuery
import com.apollographql.apollo3.ApolloClient
import com.example.githubtracker.common.Resource
import javax.inject.Inject

//you can use this in your viewmodel or use-case in case you are following clean architecture
class GitHubService @Inject constructor(val client: ApolloClient) {
    //model a data class to hold both success or error response
    //the query input should be a string modeled in the format: "user:jumapaul is:public sort:updated",
    suspend fun getRepositoriesByUserName(query: String): Resource<UserRepositoryQuery.Search?> {
        val response = client.query(UserRepositoryQuery(query)).execute()
        return if (response.hasErrors()) {
            Resource.Error(message = response.errors?.first()?.message.orEmpty())
        } else {
            Resource.Success(data = response.data?.search)
        }
    }
}