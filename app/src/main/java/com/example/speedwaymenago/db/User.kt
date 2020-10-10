package com.example.speedwaymenago.db

data class User(
    val username: String,
    val email: String,
    val score: Int = 0
)