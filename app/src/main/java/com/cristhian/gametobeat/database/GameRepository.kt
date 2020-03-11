package com.cristhian.gametobeat.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.cristhian.gametobeat.database.GameBeatDataBase.Companion.getInstance

class GameRepository internal constructor(application: Application?) {
    private val mGameDao: GameDao
    val gamesBeat: LiveData<List<Game?>?>?
    val gamesPlaying: LiveData<List<Game?>?>?
    val gamesWant: LiveData<List<Game?>?>?

    fun insert(game: Game?) {
        insertAsyncTask(mGameDao).execute(game)
    }

    fun delete(game: Game?) {
        deleteAsyncTask(mGameDao).execute(game)
    }

    fun update(game: Game?) {
        updateAsyncTask(mGameDao).execute(game)
    }

    fun loadGameById(game: Game): LiveData<Game?>? {
        return mGameDao.loadGameById(game.gameId)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: GameDao) :
            AsyncTask<Game?, Void?, Void?>() {
        override fun doInBackground(vararg games: Game?): Void? {
            games[0]?.let { mAsyncTaskDao.insertGame(it) }
            return null
        }

    }

    private class deleteAsyncTask internal constructor(private val mAsyncTaskDao: GameDao) :
            AsyncTask<Game?, Void?, Void?>() {
        override fun doInBackground(vararg games: Game?): Void? {
            games[0]?.let { mAsyncTaskDao.deleteGame(it) }
            return null
        }

    }

    private class updateAsyncTask internal constructor(private val mAsyncTaskDao: GameDao) :
            AsyncTask<Game?, Void?, Void?>() {
        override fun doInBackground(vararg games: Game?): Void? {
            games[0]?.let { mAsyncTaskDao.updateGame(it) }
            return null
        }

    }

    init {
        val db = getInstance(application!!)
        mGameDao = db!!.gameDao()
        gamesBeat = mGameDao.loadGamesByType(Game.STATUS_BEAT)
        gamesPlaying = mGameDao.loadGamesByType(Game.STATUS_PLAYING)
        gamesWant = mGameDao.loadGamesByType(Game.STATUS_WANT)
    }
}