package com.example.githubtracker.presentation.home

import UserRepositoryQuery
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtracker.common.Resource
import com.example.githubtracker.common.converters.fromJson
import com.example.githubtracker.common.getUser
import com.example.githubtracker.domain.UserData
import com.example.githubtracker.domain.use_cases.HomeUseCase
import com.example.githubtracker.util.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val _repoList = mutableStateOf(HomeUiState())
    val repoList: State<HomeUiState> = _repoList

    private var _user = mutableStateOf<UserData?>(null)
    val user = _user

    private var _repoVisibility = mutableStateOf("public")
    val repoVisibility = _repoVisibility


    fun getUserInfo(context: Context) {
        //user:${user.value?.userName}
        _user.value = getUser(context)
        getRepositoryList("is:${repoVisibility.value} sort:updated")
    }

    fun getRepositoryList(queryString: String) {
        Log.d("RES--------->", "getRepositoryList: ${queryString}")
        homeUseCase(queryString).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _repoList.value =
                        HomeUiState(errorMessage = result.message ?: "Unexpected error occurred")
                }

                is Resource.Loading -> {
                    _repoList.value = HomeUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _repoList.value = HomeUiState(
                        data = result.data?.repos
                    )
                }

                null -> TODO()
            }
        }.launchIn(viewModelScope)
    }
}