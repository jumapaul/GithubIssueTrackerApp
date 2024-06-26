package com.example.githubtracker.presentation.sign_in

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.githubtracker.common.Resource
import com.example.githubtracker.common.converters.toJson
import com.example.githubtracker.domain.UserData
import com.example.githubtracker.util.DataStoreUtils
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
    private val preference: DataStoreUtils
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
                    val userName = authResult.additionalUserInfo?.username
                    val profilePicture = authResult.user?.photoUrl
                    val accessToken = credential.accessToken.orEmpty()

                    val user = UserData(
                        accessToken = accessToken,
                        name = name.orEmpty(),
                        userName = userName,
                        profilePicture = profilePicture.toString(),
                        )

                    _authState.value = Resource.Success(
                        data = user
                    )
                    val jsonUser = user.toJson()
                    preference.saveData("user", jsonUser.orEmpty())
                }

            }.addOnFailureListener { exception ->
                _authState.value = Resource.Error(exception.message.toString())
            }
    }
}