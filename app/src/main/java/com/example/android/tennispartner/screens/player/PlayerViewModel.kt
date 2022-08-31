package com.example.android.tennispartner.screens.player

import android.app.Application
import androidx.lifecycle.*
import com.example.android.tennispartner.database.players.PlayerDatabase
import com.example.android.tennispartner.domain.Player
import com.example.android.tennispartner.repository.PlayerRepository
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application): AndroidViewModel(application) {

    private val _currentPlayer = MutableLiveData<Player>()
    val currentPlayer: LiveData<Player>
        get() = _currentPlayer

    private val repository = PlayerRepository(PlayerDatabase.getInstance(application.applicationContext))

    val players = repository.players

    private val numberOfPlayers = Transformations.map(players){
        it.size
    }

    val numberOfPlayersString = Transformations.map(numberOfPlayers){
            number -> number.toString()
    }

    fun changeCurrentPlayers() {
        viewModelScope.launch {
            repository.refreshPlayers("ATP") //fetches new players from the API
        }
    }
}