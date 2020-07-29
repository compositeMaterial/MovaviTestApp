package com.killzone.movavitest.network

import com.killzone.movavitest.model.RssFeed
import retrofit2.Response
import retrofit2.http.GET

interface HabrApi {
    @GET("all/all")
    suspend fun getHabrPosts(): RssFeed
}