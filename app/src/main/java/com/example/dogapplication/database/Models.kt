package com.example.dogapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dogapplication.entity.Dog

@Entity
data class DatabaseDog(
    @PrimaryKey
    val breed: String,
    val image: String
)

fun List<DatabaseDog>.asDomainModel(): List<Dog> {
    return map {
        Dog(
            breed = it.breed,
            image = it.image
        )
    }
}