package com.example.dogapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.dogapplication.database.DatabaseDog
import com.example.dogapplication.database.DogsDatabase
import com.example.dogapplication.database.asDomainModel
import com.example.dogapplication.entity.Dog
import com.example.dogapplication.network.DogsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogsRepository(private val database: DogsDatabase) {

    /**
     * Livedata of dogs coming from database converted to domain model
     */
    val dogs: LiveData<List<Dog>> =
        Transformations.map(database.dogDao.getDogs()) {
            it.asDomainModel()
        }

    /**
     * Fetch dogs from the api and store in database
     */
    suspend fun refreshDogs() {
        withContext(Dispatchers.IO) {
            try {
                val getBreedsDeferred = DogsApi.retrofitService.getBreedsAsync()
                // this will run on a thread managed by Retrofit
                val breedsResponse = getBreedsDeferred.await()
                val breedList = breedsResponse.message.keys.toList()
                val dogsList = ArrayList<DatabaseDog>()
                breedList.forEach { breed ->
                    val getImagesDeferred = DogsApi.retrofitService.getBreedImageAsync(breed)
                    // this will run on a thread managed by Retrofit
                    val imageResponse = getImagesDeferred.await()
                    val dog = DatabaseDog(breed, imageResponse.message)
                    dogsList.add(dog)
                }
                database.dogDao.insertAll(dogsList)
            } catch (e: Exception) {

            }
        }
    }
}