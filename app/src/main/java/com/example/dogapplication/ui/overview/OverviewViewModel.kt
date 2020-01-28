package com.example.dogapplication.ui.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogapplication.database.getDatabase
import com.example.dogapplication.entity.Dog
import com.example.dogapplication.repository.DogsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    // Internally, we use a MutableLiveData to handle navigation to the selected dog
    private val _navigateToSelectedDog = MutableLiveData<Dog>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedDog: LiveData<Dog>
        get() = _navigateToSelectedDog

    /**
     * When the dog is clicked, set the [_navigateToSelectedDog] [MutableLiveData]
     * @param dog The [Dog] that was clicked on.
     */
    fun displayDogDetails(dog: Dog) {
        _navigateToSelectedDog.value = dog
    }

    /**
     * After the navigation has taken place, make sure _navigateToSelectedDog is set to null
     */
    fun displayDogDetailsComplete() {
        _navigateToSelectedDog.value = null
    }


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(application)
    private val dogsRepository = DogsRepository(database)

    val dogsList = dogsRepository.dogs
    val networkStatus = dogsRepository.status

    init {
        coroutineScope.launch {
            dogsRepository.refreshDogs()
        }
    }


    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
