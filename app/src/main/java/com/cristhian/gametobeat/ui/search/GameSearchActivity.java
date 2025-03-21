package com.cristhian.gametobeat.ui.search;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.database.Game;
import com.cristhian.gametobeat.network.GameService;
import com.cristhian.gametobeat.network.RetrofitClientInstance;
import com.cristhian.gametobeat.util.FirebaseUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameSearchActivity extends AppCompatActivity {

    @BindView(R.id.tv_message)
    TextView mMessageTextView;

    @BindView(R.id.pb_loading)
    ProgressBar mLoadingProgressBar;

    @BindView(R.id.rv_games)
    RecyclerView mRecyclerView;

    @BindView(R.id.et_search)
    EditText mSearchEditText;

    private GameListAdapter mAdapter;

    private FirebaseAnalytics mFirebaseAnalytics;

    public static final String PARAM_GAME_NAME = "param_game_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_search);

        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setupUI();

        if (savedInstanceState != null
                && savedInstanceState.containsKey(PARAM_GAME_NAME)) {
            searchGame(savedInstanceState.getString(PARAM_GAME_NAME));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSearchEditText != null
                && !TextUtils.isEmpty(mSearchEditText.getText())) {
            outState.putString(PARAM_GAME_NAME, mSearchEditText.getText().toString());
        }
    }

    private void setupUI() {
        mAdapter = new GameListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
    }

    private void showGameDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mMessageTextView.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mMessageTextView.setVisibility(View.VISIBLE);
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mMessageTextView.setVisibility(View.INVISIBLE);
        mLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_search)
    public void search() {
        if (mSearchEditText != null
                && !TextUtils.isEmpty(mSearchEditText.getText())) {

            hideKeyboard();
            showProgressBar();
            searchGame(mSearchEditText.getText().toString());
        } else {
            Toast.makeText(GameSearchActivity.this, getString(R.string.type_game), Toast.LENGTH_LONG).show();
        }
    }

    private void searchGame(final String game) {
        GameService service = RetrofitClientInstance.getInstance().create(GameService.class);
        Call<List<Game>> call = service.getGames(game);
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.body() != null) {
                    updateLayout(response.body());
                    FirebaseUtil.logGameEvent(mFirebaseAnalytics, game, FirebaseUtil.PARAM_GAME_SEARCH);
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                mMessageTextView.setText(R.string.error_msg);
                showErrorMessage();
            }
        });
    }

    private void updateLayout(List<Game> games) {
        if (games == null) {
            mMessageTextView.setText(R.string.error_msg);
            showErrorMessage();
        } else if (games.size() == 0) {
            mMessageTextView.setText(R.string.game_list_empty);
            showErrorMessage();
        } else {
            mAdapter.setGames(games);
            mAdapter.notifyDataSetChanged();
            showGameDataView();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
