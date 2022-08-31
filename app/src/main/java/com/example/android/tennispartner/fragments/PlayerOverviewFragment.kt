package com.example.android.tennispartner.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.tennispartner.R
import com.example.android.tennispartner.database.players.PlayerDatabase
import com.example.android.tennispartner.databinding.FragmentPlayerOverviewBinding
import com.example.android.tennispartner.screens.playerOverview.PlayerAdapter
import com.example.android.tennispartner.screens.playerOverview.PlayerListener
import com.example.android.tennispartner.screens.playerOverview.PlayerOverviewViewModel
import com.example.android.tennispartner.screens.playerOverview.PlayerOverviewViewModelFactory
import com.google.android.material.chip.Chip


/**
 * A simple [Fragment] subclass.
 */
class PlayerOverviewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var  binding : FragmentPlayerOverviewBinding
    lateinit var viewModel: PlayerOverviewViewModel
    private lateinit var adapter: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_overview, container,false)

        //setup the db connection
        val application = requireNotNull(this.activity).application
        val dataSource = PlayerDatabase.getInstance(application).playerDatabaseDao

        //filling the list: player adapter
        adapter = PlayerAdapter( PlayerListener{
                playerID ->
            Toast.makeText(context, "${playerID}", Toast.LENGTH_SHORT).show()
        })
        binding.playerList.adapter = adapter

        //viewmodel
        val viewModelFactory = PlayerOverviewViewModelFactory(dataSource, application, adapter)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayerOverviewViewModel::class.java)

        //databinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        //list changed
        viewModel.players.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }


        createChips(listOf("<10", "10-20", ">20"))
        return binding.root
    }


    private fun createChips(data : List<String>) {
        //take care: the example in the movies has dynamic chips
        //these are hardcoded.
        val chipGroup = binding.chipList
        val inflator = LayoutInflater.from(chipGroup.context)

        val children = data.map {
            text ->
            val chip = inflator.inflate(R.layout.chip, chipGroup, false) as Chip
            chip.text = text
            chip.tag = text
            chip.setOnCheckedChangeListener {
            button, isChecked ->
                viewModel.filterChip(button.tag as String, isChecked)
            }
            chip
        }

        //remove existing children
        chipGroup.removeAllViews()

        //add the new children
        for(chip in children){
            chipGroup.addView(chip)
        }
    }
}