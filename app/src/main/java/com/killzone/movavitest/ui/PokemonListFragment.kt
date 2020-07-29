package com.killzone.movavitest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.killzone.movavitest.R
import com.killzone.movavitest.adapters.LoadStateAdapter
import com.killzone.movavitest.adapters.PokemonListAdapter
import com.killzone.movavitest.databinding.FragmentPokemonListBinding
import com.killzone.movavitest.viewmodels.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private lateinit var binding: FragmentPokemonListBinding
    private lateinit var adapter: PokemonListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val viewModel: PokemonViewModel by viewModels()

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        layoutManager = LinearLayoutManager(this.context)
        adapter = PokemonListAdapter()
        binding.rvPokemonList.layoutManager = layoutManager


        initAdapter()
        initPokemonList()


        return binding.root
    }

    private fun initAdapter() {
        binding.rvPokemonList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter(),
            footer = LoadStateAdapter()
        )

        adapter.addLoadStateListener { loadState ->
            //binding.rvPokemonList.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.pbInitialLoad.isVisible = loadState.source.refresh is LoadState.Loading
        }
    }

    @ExperimentalCoroutinesApi
    private fun initPokemonList() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.loadPokemons().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}


