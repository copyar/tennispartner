package com.example.android.tennispartner.screens.playerOverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.tennispartner.database.players.PlayerDatabaseDao

class PlayerOverviewViewModelFactory(private val dataSource: PlayerDatabaseDao, private val application: Application, private val adapter :PlayerAdapter): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayerOverviewViewModel::class.java)) {
            return PlayerOverviewViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
