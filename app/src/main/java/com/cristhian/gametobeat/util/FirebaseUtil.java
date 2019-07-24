package com.cristhian.gametobeat.util;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseUtil {

    private static final String PARAM_GAME_NAME = "game_name";
    public static final String PARAM_GAME_ADD = "game_add";
    public static final String PARAM_GAME_REMOVE = "game_remove";
    public static final String PARAM_GAME_UPDDATE = "game_update";
    public static final String PARAM_GAME_SEARCH = "game_search";

    public static void logGameEvent(FirebaseAnalytics firebaseAnalytics, String name, String paramStatus) {
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_GAME_NAME, name);
        firebaseAnalytics.logEvent(paramStatus, bundle);
    }


}
