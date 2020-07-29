package com.killzone.movavitest.di

import com.killzone.movavitest.model.Pokemon
import com.killzone.movavitest.network.HabrApi
import com.killzone.movavitest.network.PokemonApi
import com.killzone.movavitest.util.Constants.HABR_BASE_URL
import com.killzone.movavitest.util.Constants.POKEMON_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .baseUrl(HABR_BASE_URL)
        .build()
        .create(HabrApi::class.java)


    @Provides
    @Singleton
    fun providePokemonService() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(POKEMON_BASE_URL)
        .build()
        .create(PokemonApi::class.java)

}