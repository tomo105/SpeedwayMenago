package com.example.speedwaymenago.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.speedwaymenago.model.AuthAppRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch


class LoginRegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val authAppRepository: AuthAppRepository = AuthAppRepository(application)
    val userLiveData: MutableLiveData<FirebaseUser>

    fun login(email: String, password: String) {
        authAppRepository.login(email, password)
    }

    fun register(username: String, email: String, password: String) {
        authAppRepository.register(username, email, password)
    }
    fun logout() {
        authAppRepository.logOut()
    }
    init {
        userLiveData = authAppRepository.currentUserLiveData
    }
}