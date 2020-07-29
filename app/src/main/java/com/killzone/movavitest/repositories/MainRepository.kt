package com.killzone.movavitest.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.killzone.movavitest.model.*
import com.killzone.movavitest.network.HabrApi
import com.killzone.movavitest.network.PokemonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject



class MainRepository @Inject constructor(
    val habrService: HabrApi, val pokemonService: PokemonApi
) {

    suspend fun getHabrPosts(): Result<ConvertedHabrRssFeed> = withContext(Dispatchers.IO) {
        try {
            val feed = habrService.getHabrPosts()
            return@withContext Result.Success(feed.convert())
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    fun getPokemonStream(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = BASE_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory =  { PokemonPagingSource(pokemonService)}
        ).flow
    }
}

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


