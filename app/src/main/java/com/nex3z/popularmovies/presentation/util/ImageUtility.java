package com.nex3z.popularmovies.presentation.util;

public class ImageUtility {

    public static final String LOG_TAG = ImageUtility.class.getSimpleName();

    public static String getImageUrl(String path) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String SIZE = "w342";

        return BASE_URL + SIZE + "/" + path;
    }
}