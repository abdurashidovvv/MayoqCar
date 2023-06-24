package com.example.mayoqcar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mayoqcar.models.Call
import com.example.mayoqcar.repository.MapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val mapRepository: MapRepository): ViewModel() {

    fun addCall(call: Call){
        viewModelScope.launch {
            mapRepository.addCall(call)
        }
    }

    suspend fun getAllCalls(): MutableStateFlow<List<Call>> {
        return mapRepository.getAllCalls()
    }
}