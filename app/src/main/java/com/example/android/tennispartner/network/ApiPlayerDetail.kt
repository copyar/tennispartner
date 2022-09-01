package com.example.android.tennispartner.network

import com.example.android.tennispartner.database.players.DatabasePlayer
import com.example.android.tennispartner.domain.Player
import com.squareup.moshi.Json

data class PlayerDetailResponse (
    @Json(name = "results")
    val playerDetailResults: PlayerDetailResults
)

data class PlayerDetailResults (
    @Json(name = "player")
    val apiPlayerDetail: ApiPlayerDetail
)

/*ApiPlayer, representing a player from the network*/
data class ApiPlayerDetail(
    @Json(name = "id")
    var id: Long = 0L,

    @Json(name = "first_name")
    var firstName: String = "",

    @Json(name = "last_name")
    var lastName: String = "",

    @Json(name = "full_name")
    var fullName: String = "",

    @Json(name = "country")
    var country: String = "",
)

/*
* Convert network results into Domain players
* */
fun PlayerDetailResults.asDomainModel(): Player{
    return Player(id = apiPlayerDetail.id,
            firstName = apiPlayerDetail.firstName,
            lastName = apiPlayerDetail.lastName,
            fullName = apiPlayerDetail.fullName,
            country = apiPlayerDetail.country)
}

/*
* Convert a single api player to a database player
* */
fun ApiPlayerDetail.asDatabasePlayer(): DatabasePlayer{
    return DatabasePlayer(
        id = id,
        firstName = firstName,
        lastName = lastName,
        country = country)
}
