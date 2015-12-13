package com.nex3z.popularmovies.ui.adapter;


import android.database.Cursor;
import android.util.Log;

import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.data.provider.MovieContract;

public class MovieCursorAdapter extends AbstractMovieAdapter{

    private static final String LOG_TAG = MovieCursorAdapter.class.getSimpleName();

    private Cursor mCursor;

    @Override
    public Movie getMovie(int position) {
        mCursor.moveToPosition(position);

        Movie movie = new Movie();

        movie.setTitle(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)));
        movie.setPosterPath(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
        movie.setId(mCursor.getLong(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
        movie.setBackdropPath(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)));
        // movie.setGenreIds(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_GENRE_IDS)));
        String genreIds = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_GENRE_IDS));
        Log.v(LOG_TAG, "getMovie(): genreIds = " + genreIds);

        movie.setOverview(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
        movie.setReleaseDate(mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
        movie.setVoteAverage(mCursor.getDouble(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
        movie.setVoteCount(mCursor.getLong(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT)));

        return movie;
    }

    @Override
    public int getItemCount() {
        Log.v(LOG_TAG, "getItemCount(): mCursor = " + mCursor);
        if ( null == mCursor ) return 0;
        Log.v(LOG_TAG, "getItemCount(): mCursor.getCount() = " + mCursor.getCount());
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        Log.v(LOG_TAG, "swapCursor(): newCursor = " + newCursor);
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }
}
