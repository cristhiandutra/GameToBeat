package com.cristhian.gametobeat.network;

import com.cristhian.gametobeat.database.Game;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GameService {

    @GET("/games/?fields=name,cover.*,summary,rating&limit=20")
    Call<List<Game>> getGames(@Query("search") String query);

}
