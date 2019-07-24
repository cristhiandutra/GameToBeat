package com.cristhian.gametobeat.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.database.Game;

import java.util.List;

public class GameWidget extends AppWidgetProvider {

    public static List<Game> mGames;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_game);

        Intent intentGames = new Intent(context, GameWidgetService.class);
        views.setRemoteAdapter(R.id.lv_games, intentGames);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, GameWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_game);

            appWidgetManager.updateAppWidget(appWidgetIds, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_games);
        }

        super.onReceive(context, intent);
    }

    public static void sendBroadcast(Context context) {
        Intent intentWidget = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intentWidget.setComponent(new ComponentName(context, GameWidget.class));
        context.sendBroadcast(intentWidget);
    }
}
