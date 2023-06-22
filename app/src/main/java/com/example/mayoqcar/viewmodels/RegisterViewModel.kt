package com.example.mayoqcar.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mayoqcar.models.Worker
import com.example.mayoqcar.repository.RegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    suspend fun getAllWorkers(): MutableStateFlow<List<Worker>> {
        return registerRepository.getAllWorkers()
    }

    suspend fun addWorker(worker: Worker){
        registerRepository.addWorker(worker)
    }
}
