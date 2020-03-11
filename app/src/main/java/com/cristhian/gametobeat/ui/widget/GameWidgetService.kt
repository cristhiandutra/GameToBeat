package com.cristhian.gametobeat.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService

class GameWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return GameRemoteViewsFactory(this.applicationContext)
    }
}