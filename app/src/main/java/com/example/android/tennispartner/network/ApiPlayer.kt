package com.example.android.tennispartner.network

import com.example.android.tennispartner.database.players.DatabasePlayer
import com.example.android.tennispartner.domain.Player
import com.squareup.moshi.Json

/*ApiPlayer: this is a DataTransferObject
* it's goal is to get network data into our model data, the Player
*/

/*Container helps us parse the body into multiple players*/
data class ApiPlayerContainer (
    @Json(name = "body")
    val apiPlayers: List<ApiPlayer>
)

/*ApiPlayer, representing a player from the network*/
data class ApiPlayer(
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
fun ApiPlayerContainer.asDomainModel(): List<Player>{
    return apiPlayers.map{
        Player(firstName = it.firstName,
            lastName = it.lastName,
            fullName = it.fullName,
            country = it.country)
    }
}

/*
* Convert network result into Database players
*
* returns an array that can be used in the insert call as vararg
* */
fun ApiPlayerContainer.asDatabaseModel(): Array<DatabasePlayer>{
    return apiPlayers.map{
        DatabasePlayer(firstName = it.firstName,
            lastName = it.lastName,
            country = it.country)
    }.toTypedArray()
}

/*
* Convert a single api player to a database player
* */
fun ApiPlayer.asDatabasePlayer(): DatabasePlayer{
    return DatabasePlayer(
        firstName = firstName,
        lastName = lastName,
        country = country)
}