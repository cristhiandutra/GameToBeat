package com.cristhian.gametobeat.network

import com.cristhian.gametobeat.database.Game
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IgdbApiService {

    @GET("/games/?fields=name,cover.*,summary,rating&limit=20")
    fun getGames(@Query("search") query: String): Call<List<Game>>
}