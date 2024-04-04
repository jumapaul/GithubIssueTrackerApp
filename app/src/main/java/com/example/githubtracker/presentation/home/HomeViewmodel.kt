package com.example.githubtracker.presentation.home

import UserRepositoryQuery
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtracker.common.Resource
import com.example.githubtracker.common.getUser
import com.example.githubtracker.domain.UserData
import com.example.githubtracker.domain.use_cases.HomeUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    private val _issuesList: MutableStateFlow<List<UserRepositoryQuery.Node?>?> = MutableStateFlow(
        emptyList()
    )

    private var _user = mutableStateOf<UserData?>(null)
    val user = _user

    private var _isAuthenticated: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        _isAuthenticated.value = firebaseAuth.currentUser != null
    }


    fun getUserInfo(context: Context, user: String?) {
        _user.value = getUser(context)
        getIssuesList("user:$user is:public sort:updated")
    }

    private fun getIssuesList(queryString: String) {
        homeUseCase(queryString).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _homeUiState.value =
                        HomeUiState(errorMessage = result.message ?: "Unexpected error occurred")
                }

                is Resource.Loading -> {
                    _homeUiState.value = HomeUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _issuesList.value = result.data
                    _homeUiState.value = HomeUiState(
                        data = result.data
                    )
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun searchIssues(query: String) {
        if (query.isEmpty()) {
            _homeUiState.value = HomeUiState(
                data = _issuesList.value
            )
        } else {
            try {
                _homeUiState.value = HomeUiState(
                    data = _issuesList.value!!.filter { issues ->
                        issues!!.title.lowercase().contains(query.lowercase())
                    }
                )
            } catch (e: Exception) {
                _homeUiState.value = HomeUiState(errorMessage = e.message ?: "An error occurred")
            }

        }
    }

    fun searchLabel(labels: List<String>) {
        if (labels.isEmpty()) {
            _homeUiState.value = HomeUiState(
                data = _issuesList.value
            )
        } else {
            try {
                _homeUiState.value = HomeUiState(
                    data = _issuesList.value?.filter { issue ->
                        labels.all { label ->
                            issue?.labels?.nodes?.any { it?.name?.lowercase() == label.lowercase() }
                                ?: false
                        }
                    }
                )
            } catch (e: Exception) {
                _homeUiState.value = HomeUiState(errorMessage = e.message ?: "An error occurred")
            }
        }
    }
}