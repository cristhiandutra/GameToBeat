package com.cristhian.gametobeat.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.database.Game;
import com.cristhian.gametobeat.database.GameViewModel;
import com.cristhian.gametobeat.ui.login.LoginActivity;
import com.cristhian.gametobeat.ui.search.GameSearchActivity;
import com.cristhian.gametobeat.ui.widget.GameWidget;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameListActivity extends AppCompatActivity {

    @BindView(R.id.vp_game_list)
    ViewPager mViewPager;

    @BindView(R.id.tl_game_list)
    TabLayout mTabLayout;

    private FirebaseAuth mAuth;

    private GameViewModel mGameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        setupUI();
        setupWidget();
    }

    private void setupUI() {
        GameListPageAdapter pageAdapter = new GameListPageAdapter(getSupportFragmentManager(), this);

        GameListFragment beatGameList = new GameListFragment();
        beatGameList.setTitle(getString(R.string.beat));
        beatGameList.setStatus(Game.STATUS_BEAT);

        GameListFragment playingGameList = new GameListFragment();
        playingGameList.setTitle(getString(R.string.playing));
        playingGameList.setStatus(Game.STATUS_PLAYING);

        GameListFragment wantGameList = new GameListFragment();
        wantGameList.setTitle(getString(R.string.want));
        wantGameList.setStatus(Game.STATUS_WANT);

        pageAdapter.addFragment(beatGameList);
        pageAdapter.addFragment(playingGameList);
        pageAdapter.addFragment(wantGameList);

        mViewPager.setAdapter(pageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout_action:

                mAuth.signOut();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab_search)
    public void search() {

        Intent intent = new Intent(this, GameSearchActivity.class);
        startActivity(intent);
    }

    private void setupWidget() {
        mGameViewModel.getGamesPlaying().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> games) {
                GameWidget.mGames = games;
                GameWidget.sendBroadcast(GameListActivity.this);
            }
        });
    }
}
