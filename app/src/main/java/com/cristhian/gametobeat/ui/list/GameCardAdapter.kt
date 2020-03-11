package com.cristhian.gametobeat.ui.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.database.Game
import com.cristhian.gametobeat.ui.detail.GameDetailActivity
import com.cristhian.gametobeat.util.IgdbUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_game_card.view.*

class GameCardAdapter(val context: Context?) : RecyclerView.Adapter<GameCardAdapterViewHolder>(){

    var mGames: List<Game> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game_card, parent, false)
        return GameCardAdapterViewHolder(view)
    }

    override fun getItemCount() = mGames.count()

    override fun onBindViewHolder(holder: GameCardAdapterViewHolder, position: Int) {
        val view = holder.itemView
        var game = mGames[position]

        game.cover?.let {
            Picasso.get().load(IgdbUtil.getUrlImage(game.cover.imageId)).into(view.iv_cover)
        }

        view.cv_game.setOnClickListener {
            val intent = Intent(context, GameDetailActivity::class.java)
            intent.putExtra(GameDetailActivity.PARAM_GAME_DETAIL, game)
            context?.startActivity(intent)
        }
    }

}

class GameCardAdapterViewHolder(view : View) : RecyclerView.ViewHolder(view)