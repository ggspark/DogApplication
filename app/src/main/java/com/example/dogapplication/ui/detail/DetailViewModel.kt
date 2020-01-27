package com.example.dogapplication.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dogapplication.entity.Dog

class DetailViewModel(dog: Dog, app: Application) : AndroidViewModel(app) {
    private val _selectedDog = MutableLiveData<Dog>()

    // The external LiveData for the _selectedDog
    val selectedDog: LiveData<Dog>
        get() = _selectedDog

    // Initialize the _selectedDog MutableLiveData
    init {
        _selectedDog.value = dog
    }
}
