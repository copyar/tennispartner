package com.example.android.tennispartner.screens.bindingUtils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android.tennispartner.R
import com.example.android.tennispartner.domain.Player
import com.example.android.tennispartner.screens.playerOverviewFromAPI.PlayerApiStatus
import kotlin.random.Random

//The adapter will adapt the player to get the data we need
@BindingAdapter("playerImage")
fun ImageView.setPlayerImage(item: Player){
    //momentarily set template, figure out a way to get fotos with other API
    setImageResource(R.drawable.ic_splash_icon)
}

@BindingAdapter("playerApiStatus")
fun ImageView.setPlayerApiStatus(status: PlayerApiStatus){
    when (status) {
        PlayerApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_loading_24)
        }
        PlayerApiStatus.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_baseline_error_outline_24)
        }
        PlayerApiStatus.DONE -> {
            visibility = View.GONE
        }
    }
}

//Adapter for imageURI
@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?){
    imgUrl?.let{
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .into(this)
    }
}

@BindingAdapter("playerFullName")
fun TextView.bindFullName(players: List<Player>?){
    players.let{
        text = players?.get(0)?.fullName
    }
}

@BindingAdapter("playerCountry")
fun TextView.bindCountry(players: List<Player>?){
    players.let{
        text = players?.get(0)?.country
    }
}

@BindingAdapter("randomPlayer")
fun TextView.bindRandom(players: List<Player>?){
    players?.let{
        val randomListNumber = Random.nextInt(it.size)
        text = players[randomListNumber].fullName
    }
}
