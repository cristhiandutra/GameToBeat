package com.cristhian.gametobeat.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class GameRepository {

    private GameDao mGameDao;
    private LiveData<List<Game>> mGamesBeat;
    private LiveData<List<Game>> mGamesPlaying;
    private LiveData<List<Game>> mGamesWant;

    GameRepository(Application application) {
        GameBeatDataBase db = GameBeatDataBase.getInstance(application);
        mGameDao = db.gameDao();
        mGamesBeat = mGameDao.loadGamesByType(Game.STATUS_BEAT);
        mGamesPlaying = mGameDao.loadGamesByType(Game.STATUS_PLAYING);
        mGamesWant = mGameDao.loadGamesByType(Game.STATUS_WANT);
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
        new insertAsyncTask(mGameDao).execute(game);
    }

    public void delete(Game game) { new deleteAsyncTask(mGameDao).execute(game); }

    public void update(Game game) {
        new updateAsyncTask(mGameDao).execute(game);
    }

    public LiveData<Game> loadGameById(Game game) {
        return mGameDao.loadGameById(game.getGameId());
    }

    private static class insertAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao mAsyncTaskDao;

        insertAsyncTask(GameDao gameDao) {
            mAsyncTaskDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            mAsyncTaskDao.insertGame(games[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao mAsyncTaskDao;

        deleteAsyncTask(GameDao gameDao) {
            mAsyncTaskDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            mAsyncTaskDao.deleteGame(games[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Game, Void, Void> {

        private GameDao mAsyncTaskDao;

        updateAsyncTask(GameDao gameDao) {
            mAsyncTaskDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            mAsyncTaskDao.updateGame(games[0]);
            return null;
        }
    }
}
