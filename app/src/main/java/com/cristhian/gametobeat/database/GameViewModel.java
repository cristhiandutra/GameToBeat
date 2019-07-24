package com.cristhian.gametobeat.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cristhian.gametobeat.util.FirebaseUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private GameRepository mRepository;

    private LiveData<List<Game>> mGamesBeat;
    private LiveData<List<Game>> mGamesPlaying;
    private LiveData<List<Game>> mGamesWant;

    private FirebaseAnalytics mFirebaseAnalytics;

    public GameViewModel(@NonNull Application application) {
        super(application);
        mRepository = new GameRepository(application);
        mGamesBeat = mRepository.getGamesBeat();
        mGamesPlaying = mRepository.getGamesPlaying();
        mGamesWant = mRepository.getGamesWant();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(application);
    }

    public LiveData<List<Game>> getGamesPlaying() {
        return mGamesPlaying;
    }

    public LiveData<List<Game>> getGamesBeat() {
        return mGamesBeat;
    }

    public LiveData<List<Game>> getGamesWant() {
        return mGamesWant;
    }

    public void insert(Game game) {
        mRepository.insert(game);
        FirebaseUtil.logGameEvent(mFirebaseAnalytics, game.getName(), FirebaseUtil.PARAM_GAME_ADD);
    }

    public void update(Game game) {
        mRepository.update(game);
        FirebaseUtil.logGameEvent(mFirebaseAnalytics, game.getName(), FirebaseUtil.PARAM_GAME_UPDDATE);
    }

    public void delete(Game game) {
        mRepository.delete(game);
        FirebaseUtil.logGameEvent(mFirebaseAnalytics, game.getName(), FirebaseUtil.PARAM_GAME_REMOVE);
    }

    public LiveData<Game> loadGameById(Game game) {
        return mRepository.loadGameById(game);
    }
}
