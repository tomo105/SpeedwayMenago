package com.example.speedwaymenago.db

data class User(
    val username: String,
    val email: String,
    val role: String = "user",
    val score: Int = 0
)