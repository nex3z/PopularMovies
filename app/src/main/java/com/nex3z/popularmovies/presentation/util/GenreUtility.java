package com.nex3z.popularmovies.presentation.util;

import android.content.Context;
import android.util.Log;

import com.nex3z.popularmovies.R;

import java.util.List;

public class GenreUtility {
    private static final String LOG_TAG = GenreUtility.class.getSimpleName();

    private GenreUtility() {}

    public static String getGenreName(Context context, int code) {
        switch (code) {
            case 28:
                return context.getString(R.string.genre_action);
            case 12:
                return context.getString(R.string.genre_adventure);
            case 16:
                return context.getString(R.string.genre_animation);
            case 35:
                return context.getString(R.string.genre_comedy);
            case 80:
                return context.getString(R.string.genre_crime);
            case 99:
                return context.getString(R.string.genre_documentary);
            case 18:
                return context.getString(R.string.genre_drama);
            case 10751:
                return context.getString(R.string.genre_family);
            case 14:
                return context.getString(R.string.genre_fantasy);
            case 36:
                return context.getString(R.string.genre_history);
            case 27:
                return context.getString(R.string.genre_horror);
            case 10402:
                return context.getString(R.string.genre_music);
            case 9648:
                return context.getString(R.string.genre_mystery);
            case 10749:
                return context.getString(R.string.genre_romance);
            case 878:
                return context.getString(R.string.genre_science_fiction);
            case 10770:
                return context.getString(R.string.genre_tv_movie);
            case 53:
                return context.getString(R.string.genre_thriller);
            case 10752:
                return context.getString(R.string.genre_war);
            case 37:
                return context.getString(R.string.genre_western);
            default:
                Log.w(LOG_TAG, "getGenreName(): Unknown genre: " + code);
                return "";
        }
    }

    public static String getGenre(Context context, List<Integer> genres) {
        if (genres == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            sb.append(getGenreName(context, genres.get(i)));
            if (i != genres.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
