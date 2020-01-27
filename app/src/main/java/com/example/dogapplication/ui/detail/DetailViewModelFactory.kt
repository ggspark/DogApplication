package com.example.dogapplication.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dogapplication.entity.Dog


/**
 * Simple ViewModel factory that provides the dog and context to the ViewModel.
 */
class DetailViewModelFactory(
    private val dog: Dog,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dog, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}