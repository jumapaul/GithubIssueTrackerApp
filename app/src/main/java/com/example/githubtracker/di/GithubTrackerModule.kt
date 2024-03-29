package com.example.githubtracker.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.githubtracker.util.DataStoreUtils
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GithubTrackerModule {

    @Provides
    @Singleton
    fun provideActivity(activity: Activity): Activity {
        return activity
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("user", Context.MODE_PRIVATE)
    }
}