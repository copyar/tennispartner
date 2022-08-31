package com.example.android.tennispartner.screens.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.android.tennispartner.R
import com.example.android.tennispartner.databinding.FragmentPlayerBinding

/**
 * A simple [Fragment] subclass.
 */
class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //Step 1, use databinding to inflate the xml
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false)

        //Get an instance of the appContext to setup the database
        val appContext = requireNotNull(this.activity).application
        val viewModelFactory = PlayerViewModelFactory(appContext)
        val viewModel : PlayerViewModel by viewModels{viewModelFactory}

        binding.player = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}