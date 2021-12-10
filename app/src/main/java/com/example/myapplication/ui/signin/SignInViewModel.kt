package com.example.myapplication.ui.signin

import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.AuthRepository
import com.example.myapplication.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class SignInViewModel : BaseViewModel() {
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            AuthRepository.signIn(email, password)
        }
    }
}