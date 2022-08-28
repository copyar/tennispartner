package com.example.android.tennispartner.database.players

import androidx.lifecycle.LiveData
import androidx.room.*

/*
* Contains functions to insert and get players
* note: getPlayersLive --> live data with a list of players
* note 2: insertAll --> takes a vararg players to update all players
*/

/*the Dao only knows about DatabasePlayers*/

@Dao
interface PlayerDatabaseDao {

    @Insert
    suspend fun insert(player: DatabasePlayer)

    // adding insert all with varar
    // replace strategy: upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg players: DatabasePlayer)

    @Update
    suspend fun update(player: DatabasePlayer)

    @Query("SELECT * from player WHERE id = :key")
    suspend fun get(key: Long): DatabasePlayer?

    @Query("DELETE FROM player")
    suspend fun clear()

    @Query("SELECT * FROM player ORDER BY id DESC")
    suspend fun getAllPlayers(): List<DatabasePlayer>

    @Query("SELECT * FROM player ORDER BY id DESC")
    fun getAllPlayersLive(): LiveData<List<DatabasePlayer>>

    @Query("SELECT * FROM player WHERE country =:country ORDER BY id DESC")
    fun getPlayersFromCountry(country: String): LiveData<List<DatabasePlayer>>

    // get the number of players present
    @Query("SELECT COUNT(*) FROM player")
    suspend fun numberOfPlayers(): Int
}
