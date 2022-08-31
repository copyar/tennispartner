package com.example.android.tennispartner.screens.player

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayerViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            return PlayerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
