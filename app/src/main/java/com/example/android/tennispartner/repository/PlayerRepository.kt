package com.example.android.tennispartner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.tennispartner.database.players.PlayerDatabase
import com.example.android.tennispartner.database.players.asDomainModel
import com.example.android.tennispartner.domain.Player
import com.example.android.tennispartner.network.ApiPlayer
import com.example.android.tennispartner.network.PlayerApi
import com.example.android.tennispartner.network.PlayerApi.mockPutPlayer
import com.example.android.tennispartner.network.asDatabasePlayer
import com.example.android.tennispartner.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/*
* Class to connect the db and the network
* Contains a LiveData object with players
* Can refresh players
* */
class PlayerRepository(private val database: PlayerDatabase) {

    /*
    * What if the players list depends on query params?
    * --> The livedata source from the db will change!
    * */

    /* -- Solution 1 -- */
    /*MediatorLiveData
    * Hold a reference to the livedata instances, and switch them when needed
    * */

    /* -- Solution 2 -- */
    /*SwitchMap
    * Helper function for livedata (uses a MediatorLiveData object in the background)
    * */

    //Network call
    //get players from the database, but transform them with map
    val players= MediatorLiveData<List<Player>>()
    val filter = MutableLiveData<String>(null)
    val playersSolution2 = Transformations.switchMap(filter){
            filter -> when(filter){
        "country" -> Transformations.map(database.playerDatabaseDao.getPlayersFromCountry(country = "")){
            it.asDomainModel()
        }
        else -> Transformations.map(database.playerDatabaseDao.getAllPlayersLive()){
            it.asDomainModel()
        }
    }
    }

    //keep a reference to the original livedata
    private var changeableLiveData = Transformations.map(database.playerDatabaseDao.getAllPlayersLive()){
        it.asDomainModel()
    }

    //add the data to the mediator
    init {
        players.addSource(
            changeableLiveData
        ){
            players.setValue(it)
        }
    }

    //Filter
    fun addFilter(filter: String?){
        //remove the original source
        players.removeSource(changeableLiveData)
        //change the livedata object + apply filter
        changeableLiveData = when(filter){
            "country" -> Transformations.map(database.playerDatabaseDao.getPlayersFromCountry(country = "")){
                it.asDomainModel()
            }
            else -> Transformations.map(database.playerDatabaseDao.getAllPlayersLive()){
                it.asDomainModel()
            }
        }
        //add the data to the mediator
        players.addSource(changeableLiveData){players.setValue(it)}
    }


    //filter is now less complex
    fun addFilterSolution2(filter: String?){
        //remove the original source
        this.filter.value = filter
    }


    //Database call
    suspend fun refreshPlayers(){
        //switch context to IO thread
        withContext(Dispatchers.IO){
            val players = PlayerApi.retrofitService.getPlayers().await()
            //'*': kotlin spread operator.
            //Used for functions that expect a vararg param
            //just spreads the array into separate fields
            database.playerDatabaseDao.insertAll(*players.asDatabaseModel())
            Timber.i("end suspend")
        }
    }


    //create a new player + return the resulting player
    suspend fun createPlayer(newPlayer: Player): Player {
        //create a Data Transfer Object (Dto)
        val newApiPlayer = ApiPlayer(
            firstName = newPlayer.firstName,
            lastName = newPlayer.lastName,
            country = newPlayer.country,
            fullName = newPlayer.fullName)

        //use retrofit to put the player.
        //a put function usually returns the object that was put

        //val checkApiPlayer = PlayerApi.retrofitService.putPlayer(newApiPlayer).await()
        val checkApiPlayer = PlayerApi.retrofitService.mockPutPlayer(newApiPlayer)

        //the put results in a PlayerApi object
        //when the put is done, update the local db as well
        database.playerDatabaseDao.insert(checkApiPlayer.asDatabasePlayer())
        //the returned player can be used to pass as save arg to the next fragment (e.g)
        return newPlayer
    }
}