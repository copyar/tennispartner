package com.example.android.tennispartner.domain

import java.util.*

data class Player(
    val id: Long = 0L,
    var firstName: String = "",
    var lastName: String = "",
    var fullName: String = "",
    var country: String = "",
    var ranking: Int = 0,
    var rankingMovement: Int = 0,
    var rankingPoints: Int = 0,
)

data class Tournament(
    val city: String = "",
    val tour: String ="",
    val country: String ="",
    var startDate: Date,
    var endDate: Date,
    val id: Long = 0L,
    var name: String,
    var season: String= "",
    var surface: String = "",
)