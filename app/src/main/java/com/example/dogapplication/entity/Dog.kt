package com.example.dogapplication.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dog(
    val breed: String,
    val image: String
) : Parcelable