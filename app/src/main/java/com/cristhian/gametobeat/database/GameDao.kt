package com.cristhian.gametobeat.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {

    @Query("SELECT * FROM game WHERE status = :status")
    fun loadGamesByType(status: Int): LiveData<List<Game?>?>?

    @Query("SELECT * FROM game WHERE gameId = :gameId")
    fun loadGameById(gameId: Long?): LiveData<Game?>?

    @Insert
    fun insertGame(game: Game)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGame(game: Game)

    @Delete
    fun deleteGame(game: Game)
}