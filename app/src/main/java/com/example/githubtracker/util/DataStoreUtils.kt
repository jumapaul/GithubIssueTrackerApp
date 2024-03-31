package com.example.githubtracker.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUtils @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun saveData(key: String, userData: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, userData)
        editor.apply()
    }

    fun getUserData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}