package com.nex3z.popularmovies.presentation.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class ImageUtility {
    public static final String LOG_TAG = ImageUtility.class.getSimpleName();

    public static final String BACKDROP_SIZE_W300 = "w300";
    public static final String BACKDROP_SIZE_W780 = "w780";
    public static final String BACKDROP_SIZE_W1280 = "w1280";
    public static final String BACKDROP_SIZE_ORIGINAL = "original";
    @Retention(SOURCE)
    @StringDef({BACKDROP_SIZE_W300, BACKDROP_SIZE_W780, BACKDROP_SIZE_W1280, BACKDROP_SIZE_ORIGINAL
    })
    public @interface BackdropSize {}

    public static final String POSTER_SIZE_W92 = "w92";
    public static final String POSTER_SIZE_W154 = "w154";
    public static final String POSTER_SIZE_W185 = "w185";
    public static final String POSTER_SIZE_W342 = "w342";
    public static final String POSTER_SIZE_W500 = "w500";
    public static final String POSTER_SIZE_W780 = "w780";
    public static final String POSTER_SIZE_ORIGINAL = "original";
    @Retention(SOURCE)
    @StringDef({POSTER_SIZE_W92, POSTER_SIZE_W154, POSTER_SIZE_W185, POSTER_SIZE_W342,
            POSTER_SIZE_W500, POSTER_SIZE_W780, POSTER_SIZE_ORIGINAL
    })
    public @interface PosterSize {}

    public static String getPosterImageUrl(String path) {
        return getPosterImageUrl(path, POSTER_SIZE_W342);
    }

    public static String getPosterImageUrl(String path, @PosterSize String size) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        return BASE_URL + size + "/" + path;
    }

    public static String getBackdropImageUrl(String path) {
        return getBackdropImageUrl(path, BACKDROP_SIZE_W780);
    }

    public static String getBackdropImageUrl(String path, @BackdropSize String size) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        return BASE_URL + size + "/" + path;
    }

}