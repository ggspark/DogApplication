package com.example.dogapplication.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://dog.ceo/api/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


/**
 * A public interface that exposes the methods
 */
interface ApiService {
    @GET("breeds/list/all")
    fun getBreeds(): Deferred<BreedsResponse>

    @GET("breed/{breed}/images/random")
    fun getBreedImage(@Path("breed") breed: String): Deferred<ImageResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object DogsApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}