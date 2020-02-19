package com.cristhian.gametobeat.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game WHERE status = :status")
    LiveData<List<Game>> loadGamesByType(int status);

    @Query("SELECT * FROM game WHERE gameId = :gameId")
    LiveData<Game> loadGameById(Long gameId);

    @Insert
    void insertGame(Game game);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGame(Game game);

    @Delete
    void deleteGame(Game game);
}
