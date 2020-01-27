package com.example.dogapplication.ui.overview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dogapplication.network.DogsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        getDogBreeds()
    }


    private fun getDogBreeds() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getBreedsDeferred = DogsApi.retrofitService.getBreeds()
            try {
                // this will run on a thread managed by Retrofit
                val breedsResponse = getBreedsDeferred.await()
                Log.i("Response", breedsResponse.status)
                Log.i("Response", breedsResponse.message.toString())
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
