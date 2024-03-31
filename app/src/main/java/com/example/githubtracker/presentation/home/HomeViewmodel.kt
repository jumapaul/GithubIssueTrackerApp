package com.example.githubtracker.presentation.home

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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val _repoList = mutableStateOf(HomeUiState())
    val repoList: State<HomeUiState> = _repoList

    fun getUserInfo(context: Context): UserData? {
        return getUser(context)
    }

    fun getRepositoryList(queryString: String) {
        homeUseCase(queryString).onEach { result ->
            Log.d("----------------------->", "getRepositoryList: ${result?.data}")
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