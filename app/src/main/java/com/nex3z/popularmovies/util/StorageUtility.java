package com.nex3z.popularmovies.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.data.provider.MovieContract;

public class StorageUtility {

    private static final String LOG_TAG = StorageUtility.class.getSimpleName();

    private static final String sMovieIdSelection = MovieContract.MovieEntry.TABLE_NAME + "." +
            MovieContract.MovieEntry.COLUMN_ID + " = ?";

    public static void addToFavourite(Context context, Movie movie) {
        ContentValues movieValues = buildContentValues(movie);
        context.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movieValues);
    }

    public static void deleteFromFavourite(Context context, Movie movie) {
        String[] selectionArgs = new String[]{Long.toString(movie.getId())};
        context.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                sMovieIdSelection,
                selectionArgs);
    }

    public static boolean isFavourite(Context context, Movie movie) {
        String[] selectionArgs = new String[]{Long.toString(movie.getId())};
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                sMovieIdSelection,
                selectionArgs,
                null
        );
        if (cursor.moveToNext()) {
            Log.v(LOG_TAG, "isFavourite(): cursor not null");
            return true;
        }
        return false;
    }

    public static ContentValues buildContentValues(Movie movie) {
        ContentValues movieValues = new ContentValues();

        movieValues.put(MovieContract.MovieEntry.COLUMN_ID, movie.getId());
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        movieValues.put(MovieContract.MovieEntry.COLUMN_ADULT, movie.isAdult());
        movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_GENRE_IDS, movie.getGenreIds().toString());
        movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        movieValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, movie.isVideo());

        return movieValues;
    }
}
