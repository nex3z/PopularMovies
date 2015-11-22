package com.nex3z.popularmovies.util;

import android.util.Log;

public class ImageUtility {

    public static final String LOG_TAG = ImageUtility.class.getSimpleName();

    public static String getImageUrl(String path) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        // TODO: Deal with different size.
        final String SIZE = "w342";

        String url = BASE_URL + SIZE + "/" + path;
        Log.v(LOG_TAG, "getImageUrl(): " + url);

        return url;
    }
}


