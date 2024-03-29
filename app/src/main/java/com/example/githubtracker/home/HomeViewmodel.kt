package com.example.githubtracker.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.githubtracker.common.converters.fromJson
import com.example.githubtracker.sign_in.domain.model.UserData
import com.example.githubtracker.util.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun getUserInfo(context: Context): UserData? {
        val dataStoreUtils = DataStoreUtils(context)

        val newDataValue = ""

        val jsonData = dataStoreUtils.getUserData("user", newDataValue)
        Log.d("Res---->", "getUserInfo: ${jsonData}")

        if (jsonData.isNotEmpty()) {
            return jsonData.fromJson(UserData::class.java)
        }
        return null
    }
}