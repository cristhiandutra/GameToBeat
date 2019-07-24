package com.cristhian.gametobeat.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.database.Game;
import com.cristhian.gametobeat.database.GameViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameListFragment extends Fragment {

    @BindView(R.id.rv_game_list)
    RecyclerView mRecyclerView;

    private static final String PARAM_STATUS = "param_status";
    private static final String PARAM_TITLE = "param_title";

    private String mTitle;
    private int mStatus;

    private GameCardAdapter mAdapter;

    private GameViewModel mGameViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_list, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            mStatus = savedInstanceState.getInt(PARAM_STATUS);
            mTitle = savedInstanceState.getString(PARAM_TITLE);
        }

        mAdapter = new GameCardAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        switch (mStatus) {
            case Game.STATUS_BEAT:
                mGameViewModel.getGamesBeat().observe(this, new Observer<List<Game>>() {
                    @Override
                    public void onChanged(@Nullable List<Game> games) {
                        mAdapter.setGames(games);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                break;
            case Game.STATUS_PLAYING:
                mGameViewModel.getGamesPlaying().observe(this, new Observer<List<Game>>() {
                    @Override
                    public void onChanged(@Nullable List<Game> games) {
                        mAdapter.setGames(games);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                break;
            case Game.STATUS_WANT:
                mGameViewModel.getGamesWant().observe(this, new Observer<List<Game>>() {
                    @Override
                    public void onChanged(@Nullable List<Game> games) {
                        mAdapter.setGames(games);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                break;
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(PARAM_STATUS, mStatus);
        outState.putString(PARAM_TITLE, mTitle);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }
}
