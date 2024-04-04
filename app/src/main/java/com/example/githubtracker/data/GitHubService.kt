package com.example.githubtracker.data

import UserRepositoryQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Input
import com.apollographql.apollo3.api.Optional
import com.example.githubtracker.common.Resource
import type.Issue
import type.IssueState
import javax.inject.Inject

//you can use this in your viewmodel or use-case in case you are following clean architecture
class GitHubService @Inject constructor(val client: ApolloClient) {
    //model a data class to hold both success or error response
    //the query input should be a string modeled in the format: "user:jumapaul is:public sort:updated",
    suspend fun getRepositoriesByUserName(query: String): Resource<List<UserRepositoryQuery.Node?>> {
        val response =
            client.query(UserRepositoryQuery(query)).execute()
        return if (response.hasErrors()) {
            Resource.Error(message = response.errors?.first()?.message.orEmpty())
        } else {

            val result = response.data?.search?.repos?.flatMap {
                it?.repo?.onRepository?.issues?.nodes?.toList() ?: emptyList()
            } ?: emptyList()

            Resource.Success(data = result)
        }
    }
}