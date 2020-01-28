package com.example.dogapplication.network

data class Models(
    val message: Map<String, List<String>>,
    val status: String
)

data class ImageResponse(
    val message: String,
    val status: String
)