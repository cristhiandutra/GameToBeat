package com.cristhian.gametobeat.ui.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.database.Game;

import java.util.List;

public class GameRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Game> mGames;
    private Context mContext;

    public GameRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        if (GameWidget.mGames != null) {
            mGames = GameWidget.mGames;
        }
    }

    @Override
    public void onDataSetChanged() {
        if (GameWidget.mGames != null) {
            mGames = GameWidget.mGames;
        }
    }

    @Override
    public void onDestroy() {
        mGames = null;
    }

    @Override
    public int getCount() {
        return mGames == null ? 0 : mGames.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_game_widget);
        remoteViews.setTextViewText(R.id.tv_widget_game, mGames.get(position).getName());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
