package com.example.android.tennispartner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.tennispartner.R
import com.example.android.tennispartner.databinding.FragmentPlayerOverviewFromApiBinding
import com.example.android.tennispartner.screens.playerOverviewFromAPI.FromAPIViewModel


class PlayerOverviewFromAPIFragment: Fragment() {

    private val viewModel: FromAPIViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this,
            FromAPIViewModel.Factory(activity.application)
        ).get(FromAPIViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding : FragmentPlayerOverviewFromApiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_overview_from_api, container,false)


        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}