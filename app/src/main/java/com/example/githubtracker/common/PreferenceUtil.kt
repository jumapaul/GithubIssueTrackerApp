package com.example.githubtracker.common

import android.content.Context
import com.example.githubtracker.common.converters.fromJson
import com.example.githubtracker.domain.UserData
import com.example.githubtracker.util.DataStoreUtils

fun getUser(context: Context): UserData? {
    val dataStoreUtils = DataStoreUtils(context)

    val newDataValue = ""

    val jsonData = dataStoreUtils.getUserData("user", newDataValue)

    if (jsonData.isNotEmpty()) {
        return jsonData.fromJson(UserData::class.java)
    }
    return null
}
