package com.killzone.movavitest.network

import com.killzone.movavitest.model.PokemonFullInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonApi {
    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonInfo(
        @Path("pokemonId") pokemonId: String): PokemonFullInfo
}