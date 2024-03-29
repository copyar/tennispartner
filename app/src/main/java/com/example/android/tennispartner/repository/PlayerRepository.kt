package com.example.android.tennispartner.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.tennispartner.database.players.PlayerDatabase
import com.example.android.tennispartner.database.players.asDomainModel
import com.example.android.tennispartner.domain.Player
import com.example.android.tennispartner.network.*
import com.example.android.tennispartner.network.PlayerApi.mockPutPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/*
* Class to connect the db and the network
* Contains a LiveData object with players
* Can refresh players
* */
class PlayerRepository(private val database: PlayerDatabase) {

    // Network call
    // get players from the database, but transform them with map
    val filter = MutableLiveData<String>(null)
    val players = Transformations.switchMap(filter) {
            filter -> when (filter) {
        "country" -> Transformations.map(database.playerDatabaseDao.getPlayersFromCountry(country = "")){
            it.asDomainModel()
        }
        else -> Transformations.map(database.playerDatabaseDao.getAllPlayersLive()){
            it.asDomainModel()
        }
    }
    }
    var playersDirect: List<Player>? = null

    // filter is now less complex
    fun addFilter(filter: String?) {
        // remove the original source
        this.filter.value = filter
    }

    // Database call
    suspend fun refreshPlayers(tour: String) {
        // switch context to IO thread
        withContext(Dispatchers.IO){
            Timber.i("Execute request")
            val response = PlayerApi.retrofitService.getPlayersByTour(tour).await()
            val players = response.playerResults
            // '*': kotlin spread operator.
            // Used for functions that expect a vararg param
            // just spreads the array into separate fields
            playersDirect = response.playerResults.asDomainModel()
            database.playerDatabaseDao.insertAll(*players.asDatabaseModel())
            Timber.i("end suspend")
        }
    }

    // Database call
    suspend fun getPlayerById(id: Long) {
        // switch context to IO thread
        withContext(Dispatchers.IO){
            Timber.i("Execute request")
            val response = PlayerApi.retrofitService.getPlayerById(id).await()
            val player = response.playerDetailResults.apiPlayerDetail
            // '*': kotlin spread operator.
            // Used for functions that expect a vararg param
            // just spreads the array into separate fields
            database.playerDatabaseDao.insert(player.asDatabasePlayer())
            Timber.i("end suspend")
        }
    }
    
    // create a new player + return the resulting player
    suspend fun createPlayer(newPlayer: Player): Player {
        // create a Data Transfer Object (Dto)
        val newApiPlayer = ApiPlayer(
            id = newPlayer.id,
            firstName = newPlayer.firstName,
            lastName = newPlayer.lastName,
            country = newPlayer.country,
            fullName = newPlayer.fullName)

        val checkApiPlayer = PlayerApi.retrofitService.mockPutPlayer(newApiPlayer)

        // the put results in a PlayerApi object
        // when the put is done, update the local db as well
        database.playerDatabaseDao.insert(checkApiPlayer.asDatabasePlayer())
        // the returned player can be used to pass as save arg to the next fragment (e.g)
        return newPlayer
    }
}