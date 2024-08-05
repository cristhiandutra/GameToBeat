package com.cristhian.gametobeat.util;

public class IgdbUtil {

    public static final String BASE_URL = "https://api.igdb.com/v4/";
    private static final String BASE_IMAGE_URL = "https://images.igdb.com/igdb/image/upload/t_";

    private static final String IMAGE_URL_COVER_BIG = "cover_big";

    public static String getUrlImage(String imageId) {
        StringBuilder sb = new StringBuilder()
            .append(BASE_IMAGE_URL)
            .append(IMAGE_URL_COVER_BIG)
            .append("/")
            .append(imageId)
            .append(".jpg");

        return sb.toString();
    }
}
