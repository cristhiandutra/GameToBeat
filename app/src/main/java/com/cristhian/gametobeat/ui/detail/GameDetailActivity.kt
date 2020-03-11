package com.cristhian.gametobeat.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.database.Game
import com.cristhian.gametobeat.database.GameViewModel
import com.cristhian.gametobeat.util.IgdbUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_detail.*

class GameDetailActivity : AppCompatActivity() {

    companion object {
        const val PARAM_GAME_DETAIL = "param_game_detail"
    }

    lateinit var mGame: Game
    lateinit var mGameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        mGameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        if (intent.extras != null
                && intent.extras.containsKey(PARAM_GAME_DETAIL)
        ) {
            mGame = intent.extras.getParcelable(PARAM_GAME_DETAIL)
            mGameViewModel.loadGameById(mGame)?.observe(this,
                    Observer { game ->
                        if (game != null) {
                            mGame = game
                        }
                        setupUI()
                    })
        }

        bt_beat.setOnClickListener {
            changeStatus(Game.STATUS_BEAT)
        }

        bt_playing.setOnClickListener {
            changeStatus(Game.STATUS_PLAYING)
        }

        bt_want.setOnClickListener {
            changeStatus(Game.STATUS_WANT)
        }
    }

    private fun setupUI() {
        tv_name.text = mGame.name
        bt_beat.setBackgroundColor(resources.getColor(R.color.colorAccent))
        bt_playing.setBackgroundColor(resources.getColor(R.color.colorAccent))
        bt_want.setBackgroundColor(resources.getColor(R.color.colorAccent))
        when (mGame.status) {
            Game.STATUS_BEAT -> bt_beat.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            Game.STATUS_PLAYING -> bt_playing.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            Game.STATUS_WANT -> bt_want.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        }

        mGame.cover?.let {
            Picasso.get().load(IgdbUtil.getUrlImage(it.imageId)).into(iv_cover)
        }

        mGame.summary?.let {
            tv_summary.text = mGame.summary
        }

        mGame.rating?.let {
            val ratingFormatted = String.format("%.2f", mGame.rating)
            tv_rating.text = ratingFormatted
        }
    }

    private fun changeStatus(status: Int) {
        when {
            mGame.status == status -> {
                mGame.status = Game.STATUS_NULL
                mGameViewModel.delete(mGame)
            }
            mGame.status != Game.STATUS_NULL -> {
                mGame.status = status
                mGameViewModel.update(mGame)
            }
            else -> {
                mGame.status = status
                mGameViewModel.insert(mGame)
            }
        }
    }
}