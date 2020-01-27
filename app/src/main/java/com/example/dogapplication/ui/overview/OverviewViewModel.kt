package com.example.dogapplication.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogapplication.entity.Dog
import com.example.dogapplication.network.DogsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of Dog
    // with new values
    private val _dogsList = MutableLiveData<ArrayList<Dog>>()
    // The external LiveData interface to the dogsList is immutable, so only this class can modify
    val dogsList: LiveData<ArrayList<Dog>>
        get() = _dogsList

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


    init {
        _dogsList.value = ArrayList()
        getDogBreeds()
    }


    private fun getDogBreeds() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getBreedsDeferred = DogsApi.retrofitService.getBreeds()
            try {
                // this will run on a thread managed by Retrofit
                val breedsResponse = getBreedsDeferred.await()
                val breedList = breedsResponse.message.keys.toList()
                breedList.forEach {
                    getDogImage(it)
                }
            } catch (e: Exception) {

            }
        }
    }


    private fun getDogImage(breed: String) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getImagesDeferred = DogsApi.retrofitService.getBreedImage(breed)
            try {
                // this will run on a thread managed by Retrofit
                val imageResponse = getImagesDeferred.await()
                val dog = Dog(breed, imageResponse.message)
                _dogsList.value?.add(dog)
                _dogsList.value = ArrayList(_dogsList.value as ArrayList)
            } catch (e: Exception) {

            }
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
