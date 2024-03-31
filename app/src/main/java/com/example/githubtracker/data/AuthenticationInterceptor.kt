package com.example.githubtracker.data

import android.content.Context
import com.example.githubtracker.common.getUser
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {
    //retrieve token from shared preference and add here
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getUser(context)?.accessToken
        val request = chain.request().newBuilder()
            .apply {
                addHeader("Authorization", "Bearer $token")
            }.build()
        return chain.proceed(request)
    }
}