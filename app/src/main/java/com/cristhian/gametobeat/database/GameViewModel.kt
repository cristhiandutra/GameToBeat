package com.cristhian.gametobeat.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cristhian.gametobeat.util.FirebaseUtil
import com.cristhian.gametobeat.util.FirebaseUtil.logGameEvent
import com.google.firebase.analytics.FirebaseAnalytics

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: GameRepository = GameRepository(application)

    val gamesBeat: LiveData<List<Game?>?>?
    val gamesPlaying: LiveData<List<Game?>?>?
    val gamesWant: LiveData<List<Game?>?>?
    private val mFirebaseAnalytics: FirebaseAnalytics

    fun insert(game: Game) {
        mRepository.insert(game)
        logGameEvent(mFirebaseAnalytics, game.name, FirebaseUtil.PARAM_GAME_ADD)
    }

    fun update(game: Game) {
        mRepository.update(game)
        logGameEvent(mFirebaseAnalytics, game.name, FirebaseUtil.PARAM_GAME_UPDDATE)
    }

    fun delete(game: Game) {
        mRepository.delete(game)
        logGameEvent(mFirebaseAnalytics, game.name, FirebaseUtil.PARAM_GAME_REMOVE)
    }

    fun loadGameById(game: Game?): LiveData<Game?>? {
        return mRepository.loadGameById(game!!)
    }

    init {
        gamesBeat = mRepository.gamesBeat
        gamesPlaying = mRepository.gamesPlaying
        gamesWant = mRepository.gamesWant
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(application)
    }
}