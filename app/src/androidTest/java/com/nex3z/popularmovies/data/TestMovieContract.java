package com.nex3z.popularmovies.data;


import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class TestMovieContract extends AndroidTestCase {

    private static final String LOG_TAG = TestMovieContract.class.getSimpleName();

    private static final long TEST_MOVIE_ID = 9527L;

    public void testBuildMovieId() {
        Uri movieUri = MovieContract.MovieEntry.buildMovieUri(TEST_MOVIE_ID);
        assertNotNull("Error: Null Uri returned.", movieUri);

        Log.v(LOG_TAG, "testBuildMovieId(): last path segment = " + movieUri.getLastPathSegment());
        assertEquals("Error: Movie id not properly appended to the end of the Uri",
                String.valueOf(TEST_MOVIE_ID), movieUri.getLastPathSegment());

        Log.v(LOG_TAG, "testBuildMovieId(): movieUri = " + movieUri);
        assertEquals("Error: Movie Uri doesn't match our expected result",
                movieUri.toString(), "content://com.nex3z.popularmovies/movie/9527");
    }
}
