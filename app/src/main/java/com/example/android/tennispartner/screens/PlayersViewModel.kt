package com.example.android.tennispartner.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.tennispartner.database.players.PlayerDatabase
import com.example.android.tennispartner.repository.PlayerRepository

class PlayersViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PlayerRepository(PlayerDatabase.getInstance(application.applicationContext))
    val allPlayers = repo.players
}