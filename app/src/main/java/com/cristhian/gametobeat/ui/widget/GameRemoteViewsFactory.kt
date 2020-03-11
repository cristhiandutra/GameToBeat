package com.cristhian.gametobeat.ui.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.database.Game

class GameRemoteViewsFactory(private val mContext: Context) : RemoteViewsFactory {
    private var mGames: List<Game>? = null

    override fun onCreate() {
        if (GameWidget.mGames != null) {
            mGames = GameWidget.mGames
        }
    }

    override fun onDataSetChanged() {
        if (GameWidget.mGames != null) {
            mGames = GameWidget.mGames
        }
    }

    override fun onDestroy() {
        mGames = null
    }

    override fun getCount(): Int {
        return if (mGames == null) 0 else mGames!!.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews =
                RemoteViews(mContext.packageName, R.layout.item_game_widget)
        remoteViews.setTextViewText(R.id.tv_widget_game, mGames!![position].name)
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }

}