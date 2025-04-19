package com.example.baatcheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()
    val authResult = MutableLiveData<Pair<Boolean, String?>>()

    fun register(email: String, password: String, username: String) {
        repository.registerUser(email, password, username) { success, message ->
            authResult.postValue(Pair(success, message))
        }
    }

    fun login(email: String, password: String) {
        repository.loginUser(email, password) { success, message ->
            authResult.postValue(Pair(success, message))
        }
    }
}