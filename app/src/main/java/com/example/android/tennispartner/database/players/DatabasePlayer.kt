package com.example.android.tennispartner.database.players

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.tennispartner.domain.Player
import com.example.android.tennispartner.network.ApiPlayer
import com.squareup.moshi.Json

/*
* Database entity DatabasePlayer
* This represents a Player in the database
* it also has a function to transform a list of database players
* to the players used in the rest of the app: model players
* */


@Entity(tableName = "player")
data class DatabasePlayer(
    @ColumnInfo(name = "id")
    @Json(name = "id") var id: Long = 0L,

    @ColumnInfo(name = "first_name")
    @Json(name = "first_name") var firstName: String = "",

    @ColumnInfo(name = "last_name")
    @Json(name = "last_name") var lastName: String = "",

    @ColumnInfo(name = "full_name")
    @Json(name = "full_name") var fullName: String = "",

    @ColumnInfo(name = "country")
    @Json(name = "country") var country: String = "",

    @ColumnInfo(name = "ranking")
    @Json(name = "ranking") var ranking: Int = 0,

    @ColumnInfo(name = "ranking_movement")
    @Json(name = "ranking_movement") var rankingMovement: Int = 0,

    @ColumnInfo(name = "ranking_points")
    @Json(name = "ranking_points") var rankingPoints: Int = 0,

    @ColumnInfo(name = "race_ranking")
    @Json(name = "race_ranking") var raceRanking: Int = 0,

    @ColumnInfo(name = "race_movement")
    @Json(name = "race_movement") var raceMovement: Int = 0,

    @ColumnInfo(name = "race_points")
    @Json(name = "race_points") var racePoints: Int = 0,

    )


//convert Player to ApiPlayer
fun List<DatabasePlayer>.asDomainModel() : List<Player>{
    return map {
        Player(
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            fullName = it.fullName,
            country = it.country,
            ranking = it.ranking,
            rankingMovement = it.rankingMovement,
            rankingPoints = it.rankingPoints,

        )
    }
}


