package com.cristhian.gametobeat.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.database.Game
import com.cristhian.gametobeat.database.GameViewModel
import kotlinx.android.synthetic.main.fragment_game_list.view.*

class GameListFragment : Fragment() {

    lateinit var mTitle: String
    var mStatus: Int = 0

    lateinit var mGameViewModel: GameViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_game_list, container, false)

        var adapter = GameCardAdapter(context)
        view.rv_game_list.adapter = adapter

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        view.rv_game_list.layoutManager = staggeredGridLayoutManager

        mGameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        when (mStatus){
            Game.STATUS_BEAT -> {
                mGameViewModel.gamesBeat?.observe(viewLifecycleOwner, Observer {games ->
                    adapter.mGames = games as List<Game>
                    adapter.notifyDataSetChanged()
                })
            }
            Game.STATUS_PLAYING -> {
                mGameViewModel.gamesPlaying?.observe(viewLifecycleOwner, Observer {games ->
                    adapter.mGames = games as List<Game>
                    adapter.notifyDataSetChanged()
                })
            }
            Game.STATUS_WANT -> {
                mGameViewModel.gamesWant?.observe(viewLifecycleOwner, Observer {games ->
                    adapter.mGames = games as List<Game>
                    adapter.notifyDataSetChanged()
                })
            }
        }

        return view
    }

    fun setTitle(title: String) {
        mTitle = title
    }

    fun getTitle() : String {
        return mTitle
    }

    fun setStatus(status : Int) {
        mStatus = status
    }
}