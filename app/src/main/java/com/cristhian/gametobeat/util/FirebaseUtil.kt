package com.cristhian.gametobeat.util

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object FirebaseUtil {

    private const val PARAM_GAME_NAME = "game_name"
    const val PARAM_GAME_ADD = "game_add"
    const val PARAM_GAME_REMOVE = "game_remove"
    const val PARAM_GAME_UPDDATE = "game_update"
    const val PARAM_GAME_SEARCH = "game_search"

    fun logGameEvent(
            firebaseAnalytics: FirebaseAnalytics,
            name: String,
            paramStatus: String
    ) {
        val bundle = Bundle()
        bundle.putString(PARAM_GAME_NAME, name)
        firebaseAnalytics.logEvent(paramStatus, bundle)
    }
}