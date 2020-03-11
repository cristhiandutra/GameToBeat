package com.cristhian.gametobeat.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cristhian.gametobeat.database.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameBeatDataBase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "moviebeatdb"

        @Volatile
        private var sInstance: GameBeatDataBase? = null

        fun getInstance(context: Context): GameBeatDataBase? {
            if (sInstance != null) {
                return sInstance
            }
            synchronized(this) {
                sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        GameBeatDataBase::class.java,
                        DATABASE_NAME
                ).build()
                return sInstance
            }
        }
    }
}