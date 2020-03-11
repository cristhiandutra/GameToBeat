package com.cristhian.gametobeat.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.cristhian.gametobeat.R
import com.cristhian.gametobeat.database.Game

class GameWidget : AppWidgetProvider() {
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) { // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {}
    override fun onDisabled(context: Context) {}
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, GameWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
            val views = RemoteViews(context.packageName, R.layout.widget_game)
            appWidgetManager.updateAppWidget(appWidgetIds, views)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_games)
        }
        super.onReceive(context, intent)
    }

    companion object {
        var mGames: List<Game>? = null
        fun updateAppWidget(
                context: Context, appWidgetManager: AppWidgetManager,
                appWidgetId: Int
        ) { // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.widget_game)
            val intentGames = Intent(context, GameWidgetService::class.java)
            views.setRemoteAdapter(R.id.lv_games, intentGames)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun sendBroadcast(context: Context) {
            val intentWidget = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            intentWidget.component = ComponentName(context, GameWidget::class.java)
            context.sendBroadcast(intentWidget)
        }
    }
}