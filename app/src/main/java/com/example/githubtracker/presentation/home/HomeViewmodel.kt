package com.example.githubtracker.presentation.home

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    private val _issuesList = mutableStateOf(HomeUiState())
    val issuesList: State<HomeUiState> = _issuesList

    private var _user = mutableStateOf<UserData?>(null)
    val user = _user


    fun getUserInfo(context: Context, user: String?) {
        _user.value = getUser(context)
        getIssuesList("user:$user is:public sort:updated")
    }

    fun getIssuesList(queryString: String) {
        homeUseCase(queryString).onEach { result ->

            when (result) {
                is Resource.Error -> {
                    _issuesList.value =
                        HomeUiState(errorMessage = result.message ?: "Unexpected error occurred")
                }

                is Resource.Loading -> {
                    _issuesList.value = HomeUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _issuesList.value = HomeUiState(
                        data = result.data
                    )
                }

                null -> TODO()
            }
        }.launchIn(viewModelScope)
    }

    fun searchIssues(query: String){
        _issuesList.value.data!!.filter { issues->
            issues!!.title.contains(query)
        }
    }
}