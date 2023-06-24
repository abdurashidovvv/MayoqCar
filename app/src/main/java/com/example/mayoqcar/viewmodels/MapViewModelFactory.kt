package com.example.mayoqcar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mayoqcar.repository.MapRepository

class MapViewModelFactory(private val mapRepository: MapRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)){
            return MapViewModel(mapRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}