package com.example.mayoqcar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mayoqcar.repository.RegisterRepository

@Suppress("UNCHECKED_CAST", "UNREACHABLE_CODE")
class RegisterViewModelFactory(private val registerRepository: RegisterRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(registerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}