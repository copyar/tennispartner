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


@Entity(tableName = "custom_player_table")
data class DatabasePlayer(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "first_name")
    @Json(name = "setup") var firstName: String = "",

    @ColumnInfo(name = "last_name")
    @Json(name = "type") var lastName: String = "",

    @ColumnInfo(name = "full_name")
    @Json(name = "type") var fullName: String = "",

    @ColumnInfo(name = "country")
    @Json(name = "country") var country: String = "",

    )


//convert Player to ApiPlayer
fun List<DatabasePlayer>.asDomainModel() : List<Player>{
    return map {
        Player(
            firstName = it.firstName,
            lastName = it.lastName,
            fullName = it.fullName,
            country = it.country
        )
    }
}


