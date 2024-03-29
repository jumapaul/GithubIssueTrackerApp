package com.example.githubtracker.sign_in.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.githubtracker.common.Resource
import com.example.githubtracker.sign_in.domain.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {
    private var _authState = MutableStateFlow<Resource<UserData>>(Resource.Loading())
    val authState = _authState.asStateFlow()

    private val provider = OAuthProvider.newBuilder("github.com")

    fun signInWithGithub(activity: Activity) {

        firebaseAuth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { authResult ->

                val credential = authResult.credential
                if (credential is OAuthCredential) {
                    val name = authResult.user?.displayName
                    val profilePicture = authResult.user?.photoUrl
                    val accessToken = credential.accessToken.orEmpty()

                    val user = UserData(
                        accessToken, name, profilePicture
                    )

                    _authState.value = Resource.Success(
                        data = user
                    )
                }

            }.addOnFailureListener { exception ->
                _authState.value = Resource.Error(exception.message.toString())
            }
    }
}