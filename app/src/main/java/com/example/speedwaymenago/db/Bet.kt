package com.example.speedwaymenago.db

data class Bet(
    val teamHome: String,
    val teamAway: String,
    val teamHomeScore: Int,
    val teamAwayScore: Int,
    val riderTeamHome: String,
    val riderTeamAway: String,
    val riderToPoints: String,
    val numberOfPoints: Double
)