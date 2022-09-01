package com.example.android.tennispartner.screens.playerOverviewFromAPI

import android.app.Application
import androidx.lifecycle.*
import com.example.android.tennispartner.database.players.PlayerDatabase
import com.example.android.tennispartner.domain.Player
import com.example.android.tennispartner.repository.PlayerRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

enum class PlayerApiStatus { LOADING, ERROR, DONE }

class FromAPIViewModel(application: Application): AndroidViewModel(application) {

    private val _status = MutableLiveData<PlayerApiStatus>()
    val status: LiveData<PlayerApiStatus>
        get() = _status

    private val database = PlayerDatabase.getInstance(application.applicationContext)
    private val playerRepository = PlayerRepository(database)
    val players = playerRepository.players
    val playersDirect = playerRepository.playersDirect

    init {
        Timber.i("getting players")
        viewModelScope.launch {
            _status.value = PlayerApiStatus.LOADING
            playerRepository.refreshPlayers("ATP")
            _status.value = PlayerApiStatus.DONE
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    /**
     * Factory for constructing FromAPIViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FromAPIViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FromAPIViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

