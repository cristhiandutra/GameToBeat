package com.cristhian.gametobeat.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.database.Game
import com.cristhian.gametobeat.network.RetrofitClientInstance
import com.cristhian.gametobeat.util.FirebaseUtil
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_game_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameSearchActivity : AppCompatActivity() {

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private lateinit var mAdapter: GameListAdapter
    private val PARAM_GAME_NAME = "param_game_name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_search)

        configureFirebaseAnalytics()
        setupUI()

        bt_search.setOnClickListener {
            if (!et_search.text.isNullOrEmpty()) {
                hideKeyboard()
                showProgressBar()
                searchGame(et_search.text.toString())
            } else {
                Toast.makeText(
                        this@GameSearchActivity,
                        getString(R.string.type_game),
                        Toast.LENGTH_LONG
                ).show()
            }
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(PARAM_GAME_NAME)) {
            searchGame(savedInstanceState.getString(PARAM_GAME_NAME))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!et_search.text.isNullOrEmpty()) {
            outState.putString(PARAM_GAME_NAME, et_search.text.toString())
        }
    }

    private fun configureFirebaseAnalytics() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }

    private fun setupUI() {
        mAdapter = GameListAdapter(this)
        rv_games.adapter = mAdapter
        rv_games.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun showGameDataView() {
        rv_games.visibility = View.VISIBLE
        tv_message.visibility = View.INVISIBLE
        pb_loading.visibility = View.INVISIBLE
    }

    private fun showErrorMessage() {
        rv_games.visibility = View.INVISIBLE
        tv_message.visibility = View.VISIBLE
        pb_loading.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        rv_games.visibility = View.INVISIBLE
        tv_message.visibility = View.INVISIBLE
        pb_loading.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    private fun updateLayout(games: List<Game>) {
        if (games.isEmpty()) {
            tv_message.text = getString(R.string.game_list_empty)
            showErrorMessage()
        } else {
            mAdapter.games = games.filter { it.cover != null }
            mAdapter.notifyDataSetChanged()
            showGameDataView()
        }
    }

    private fun searchGame(game: String) {
        RetrofitClientInstance.igdbApiService.getGames(game).enqueue(object : Callback<List<Game>> {
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.body() != null) {
                    updateLayout(response.body()!!)
                    FirebaseUtil.logGameEvent(
                            mFirebaseAnalytics,
                            game,
                            FirebaseUtil.PARAM_GAME_SEARCH
                    )
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                tv_message.text = getText(R.string.error_msg)
                showErrorMessage()
            }
        })
    }
}