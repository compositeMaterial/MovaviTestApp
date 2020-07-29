package com.killzone.movavitest.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.killzone.movavitest.model.Pokemon
import com.killzone.movavitest.repositories.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class PokemonViewModel @ViewModelInject constructor(
    val repository: MainRepository
) : ViewModel() {

    private var currentPokemonList: Flow<PagingData<Pokemon>>? = null

    fun loadPokemons(): Flow<PagingData<Pokemon>> {
        val pokemonList: Flow<PagingData<Pokemon>> = repository.getPokemonStream()
            .cachedIn(viewModelScope)
        currentPokemonList = pokemonList
        return pokemonList
    }
}