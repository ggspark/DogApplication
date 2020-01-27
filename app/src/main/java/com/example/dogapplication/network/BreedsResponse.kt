package com.example.dogapplication.network

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)