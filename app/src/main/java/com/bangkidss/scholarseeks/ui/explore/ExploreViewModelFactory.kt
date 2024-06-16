package com.bangkidss.scholarseeks.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkidss.scholarseeks.api.ApiService

class ExploreViewModelFactory(private val apiService: ApiService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExploreViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ExploreViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}