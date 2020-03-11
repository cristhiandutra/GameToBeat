package com.cristhian.gametobeat.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.database.Game
import com.cristhian.gametobeat.ui.detail.GameDetailActivity
import com.cristhian.gametobeat.util.IgdbUtil.getUrlImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_game_list.view.*

class GameListAdapter(val context: Context) :
        RecyclerView.Adapter<GameListAdapterViewHolder>() {

    var games: List<Game> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListAdapterViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_game_list, parent, false)
        return GameListAdapterViewHolder(view)
    }

    override fun getItemCount() = games.count()

    override fun onBindViewHolder(holder: GameListAdapterViewHolder, position: Int) {
        val view = holder.itemView
        val game = games[position]

        view.tv_name.text = game.name

        game.cover?.let {
            Picasso.get().load(getUrlImage(it.imageId)).into(view.iv_cover)
        }

        game.rating?.let {
            val ratingFormatted = String.format("%.2f", game.rating)
            view.tv_rating.text = ratingFormatted
        }

        view.cv_game_list.setOnClickListener {
            val intent = Intent(context, GameDetailActivity::class.java)
            intent.putExtra(GameDetailActivity.PARAM_GAME_DETAIL, game)
            context.startActivity(intent)
        }
    }
}

class GameListAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view)