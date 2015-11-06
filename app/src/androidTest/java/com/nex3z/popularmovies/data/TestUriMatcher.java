package com.nex3z.popularmovies.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;


public class TestUriMatcher extends AndroidTestCase {

    private static final String LOG_TAG = TestUriMatcher.class.getSimpleName();

    private static final long ID_QUERY = 9527;

    private static final Uri TEST_MOVIE_DIR = MovieContract.MovieEntry.CONTENT_URI;
    private static final Uri TEST_MOVIE_WITH_ID = MovieContract.MovieEntry.buildMovieUri(ID_QUERY);

    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The MOVIE URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIE);

        // tadfaLog.v(LOG_TAG, "testUriMatcher(): " + TEST_MOVIE_WITH_ID + " match " + testMatcher.match(TEST_MOVIE_WITH_ID));
        assertEquals("Error: The MOVIE WITH ID URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_WITH_ID), MovieProvider.MOVIE_WITH_ID);

    }
}
