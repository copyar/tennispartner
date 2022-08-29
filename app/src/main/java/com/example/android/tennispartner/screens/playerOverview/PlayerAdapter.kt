package com.example.android.tennispartner.screens.playerOverview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tennispartner.databinding.PlayerListItemBinding
import com.example.android.tennispartner.domain.Player

class PlayerAdapter(val clickListener: PlayerListener) : ListAdapter<Player, ViewHolder>(PlayerDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder(val binding: PlayerListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener: PlayerListener, item: Player) {
        binding.playerFullNameTextview.text = item.fullName
        binding.playerCountryTextview.text = item.country
        binding.player = item
        binding.clickListener = clickListener
        binding.executePendingBindings()

    }

    //this way the viewHolder knows how to inflate.
    //better than having this in the adapter.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PlayerListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}


class PlayerDiffCallback: DiffUtil.ItemCallback<Player>(){
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}

class PlayerListener(val clickListener: (playerID: Long)->Unit){
    fun onClick(player: Player) = clickListener(player.id)
}