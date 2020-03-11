package com.cristhian.gametobeat.util

import com.cristhian.gametobeat.network.RetrofitClientInstance

object IgdbUtil {

    fun getUrlImage(imageId: String): String {
        val sb = StringBuilder()
                .append(RetrofitClientInstance.BASE_IMAGE_URL)
                .append(RetrofitClientInstance.IMAGE_URL_COVER_BIG)
                .append("/")
                .append(imageId)
                .append(".jpg")
        return sb.toString()
    }
}