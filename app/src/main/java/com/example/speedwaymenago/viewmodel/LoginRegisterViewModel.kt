package com.example.speedwaymenago.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.speedwaymenago.model.AuthAppRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class LoginRegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val authAppRepository: AuthAppRepository = AuthAppRepository(application)
    val userLiveData: MutableLiveData<FirebaseUser>

    fun login(email: String, password: String) =viewModelScope.launch{
        authAppRepository.login(email, password)
       // if (userLiveData.value!!.email == "tomo105@wp.pl") {
         //   Log.d("custom","moze tak ??? \n ${userLiveData.value!!.email}")
       // }
    }

    fun register(username: String, email: String, password: String) =viewModelScope.launch{
        authAppRepository.register(username, email, password)
    }
    fun logout()= viewModelScope.launch {
        authAppRepository.logOut()
    }
    init {
        userLiveData = authAppRepository.userLiveData
    }
}