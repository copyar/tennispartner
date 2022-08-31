package com.example.android.tennispartner.screens.playerOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.tennispartner.database.players.PlayerDatabase
import com.example.android.tennispartner.database.players.PlayerDatabaseDao
import com.example.android.tennispartner.repository.PlayerRepository

class PlayerOverviewViewModel(val database: PlayerDatabaseDao, app: Application ): AndroidViewModel(app) {

    private var currentFilter: String? = null

    val db = PlayerDatabase.getInstance(app.applicationContext)
    val repository = PlayerRepository(db)

    val players = repository.players


    fun filterChip(changedFilter: String, isChecked: Boolean) {
        //set currentFilter
        if (isChecked) {
            currentFilter = changedFilter
        } else if (currentFilter == changedFilter) {
            currentFilter = null
        }

        //repository.addFilter(currentFilter)
        repository.addFilter(currentFilter)
    }

}