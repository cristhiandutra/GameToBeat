package com.cristhian.gametobeat.ui.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.database.Game;
import com.cristhian.gametobeat.database.GameViewModel;
import com.cristhian.gametobeat.util.IgdbUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameDetailActivity extends AppCompatActivity {

    public static final String PARAM_GAME_DETAIL = "param_game_detail";

    @BindView(R.id.tv_name)
    TextView mName;

    @BindView(R.id.iv_cover)
    ImageView mCover;

    @BindView(R.id.tv_summary)
    TextView mSummary;

    @BindView(R.id.tv_rating)
    TextView mRating;

    @BindView(R.id.bt_beat)
    Button mBeat;

    @BindView(R.id.bt_playing)
    Button mPlaying;

    @BindView(R.id.bt_want)
    Button mWant;

    private Game mGame;

    private GameViewModel mGameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);

        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        if (getIntent().getExtras() != null
                && getIntent().getExtras().containsKey(PARAM_GAME_DETAIL)) {

            mGame = getIntent().getExtras().getParcelable(PARAM_GAME_DETAIL);

            mGameViewModel.loadGameById(mGame).observe(this, new Observer<Game>() {
                @Override
                public void onChanged(@Nullable Game game) {
                    if (game != null) {
                        mGame = game;
                    }

                    setupUI();
                }
            });
        }
    }

    private void setupUI() {
        mName.setText(mGame.getName());

        mBeat.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mPlaying.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mWant.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        switch (mGame.getStatus()) {
            case Game.STATUS_BEAT:
                mBeat.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case Game.STATUS_PLAYING:
                mPlaying.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case Game.STATUS_WANT:
                mWant.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
        }

        if (mGame.getCover() != null) {
            Glide.with(this).load(IgdbUtil.getUrlImage(mGame.getCover().getImageId())).into(mCover);
        }

        if (mGame.getSummary() != null) {
            mSummary.setText(mGame.getSummary());
        }

        if (mGame.getRating() != null) {
            String ratingFormatted = String.format("%.2f", mGame.getRating());
            mRating.setText(ratingFormatted);
        }


    }

    @OnClick(R.id.bt_beat)
    public void beat() {
        changeStatus(Game.STATUS_BEAT);
    }

    @OnClick(R.id.bt_playing)
    public void playing() {
        changeStatus(Game.STATUS_PLAYING);
    }

    @OnClick(R.id.bt_want)
    public void want() {
        changeStatus(Game.STATUS_WANT);
    }

    private void changeStatus(int status) {

        if (mGame.getStatus() == status) {
            mGame.setStatus(Game.STATUS_NULL);
            mGameViewModel.delete(mGame);
        } else if (mGame.getStatus() != Game.STATUS_NULL) {
            mGame.setStatus(status);
            mGameViewModel.update(mGame);
        } else {
            mGame.setStatus(status);
            mGameViewModel.insert(mGame);
        }
    }
}
