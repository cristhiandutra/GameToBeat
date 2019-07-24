package com.cristhian.gametobeat.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class GameWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GameRemoteViewsFactory(this.getApplicationContext());
    }
}
