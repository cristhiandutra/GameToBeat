package com.cristhian.gametobeat.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
