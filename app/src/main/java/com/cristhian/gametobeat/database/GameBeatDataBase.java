package com.cristhian.gametobeat.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class GameBeatDataBase extends RoomDatabase {

    private static final String LOG_TAG = GameBeatDataBase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "moviebeatdb";
    private static GameBeatDataBase sInstance;

    public static GameBeatDataBase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        GameBeatDataBase.class,
                        GameBeatDataBase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract GameDao gameDao();
}
