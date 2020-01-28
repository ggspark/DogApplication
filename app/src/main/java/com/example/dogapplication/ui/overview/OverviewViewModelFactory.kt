package com.example.dogapplication.ui.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OverviewViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OverviewViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}