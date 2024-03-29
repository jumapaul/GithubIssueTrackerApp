package com.example.githubtracker.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="user")
class DataStoreUtils(context: Context) {

//    private objectPreferenceKey{
//        val saveUserData = booleanPreferencesKey()
//    }
}