package com.cristhian.gametobeat.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.database.Game
import com.cristhian.gametobeat.database.GameViewModel
import com.cristhian.gametobeat.ui.login.LoginActivity
import com.cristhian.gametobeat.ui.search.GameSearchActivity
import com.cristhian.gametobeat.ui.widget.GameWidget
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_game_list.*

class GameListActivity : AppCompatActivity() {

    lateinit var mGameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        mGameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        setupUI()
        setupWidget()

        fab_search.setOnClickListener {
            val intent = Intent(this, GameSearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUI() {
        val pageAdapter = GameListPageAdapter(supportFragmentManager)

        val beatGameList = GameListFragment()
        beatGameList.setTitle(getString(R.string.beat))
        beatGameList.setStatus(Game.STATUS_BEAT)

        val playingGameList = GameListFragment()
        playingGameList.setTitle(getString(R.string.playing))
        playingGameList.setStatus(Game.STATUS_PLAYING)

        val wantGameList = GameListFragment()
        wantGameList.setTitle(getString(R.string.want))
        wantGameList.setStatus(Game.STATUS_WANT)

        pageAdapter.addFragment(beatGameList)
        pageAdapter.addFragment(playingGameList)
        pageAdapter.addFragment(wantGameList)

        vp_game_list.adapter = pageAdapter
        tl_game_list.setupWithViewPager(vp_game_list)
    }

    private fun setupWidget() {
        mGameViewModel.gamesPlaying?.observe(this, Observer {
            GameWidget.mGames = it as List<Game>?
            GameWidget.sendBroadcast(this@GameListActivity)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.logout_action -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
