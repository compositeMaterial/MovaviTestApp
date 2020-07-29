package com.killzone.movavitest.repositories

import androidx.paging.PagingSource
import com.killzone.movavitest.model.Pokemon
import com.killzone.movavitest.network.PokemonApi
import retrofit2.HttpException
import java.io.IOException

const val BASE_PAGE_SIZE = 5
const val POKEMON_STARTING_ID = 1

class PokemonPagingSource(private val service: PokemonApi) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val position = params.key ?: POKEMON_STARTING_ID

        return try {
            val start = if (position == POKEMON_STARTING_ID) 1 else (position - 1) * BASE_PAGE_SIZE
            val end = position * BASE_PAGE_SIZE
            val response = mutableListOf<Pokemon>()

            for (i in start..end) {
                response.add(service.getPokemonInfo(i.toString()).convertToPokemon())
            }

            LoadResult.Page(
                data = response,
                prevKey = if (position == POKEMON_STARTING_ID) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}