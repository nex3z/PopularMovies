package com.nex3z.popularmovies.data;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;
import android.util.Log;

import com.nex3z.popularmovies.util.PollingCheck;

import java.util.Map;
import java.util.Set;

public class TestUtilities extends AndroidTestCase {

    public static final String LOG_TAG = TestUtilities.class.getSimpleName();

    static ContentValues createMovieValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.MovieEntry.COLUMN_ADULT, true);
        testValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, "/dkMD5qlogeRMiEixC4YNPUvax2T.jpg");
        testValues.put(MovieContract.MovieEntry.COLUMN_GENRE_IDS, "[28,12,878,53]");
        testValues.put(MovieContract.MovieEntry.COLUMN_ID, 135397);
        testValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, "en");
        testValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, "Jurassic World");
        testValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.");
        testValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "2015-06-12");
        testValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg");
        // testValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, 43.57727);
        testValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, 43.577);
        testValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Jurassic World");
        testValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, true);
        testValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, 6.9);
        testValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, 2735);

        return testValues;
    }

    static ContentValues createVideoValues() {
        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.VideoEntry.COLUMN_MOVIE_KEY, 135397);
        testValues.put(MovieContract.VideoEntry.COLUMN_VIDEO_ID, "54749bea9251414f41001b58");
        testValues.put(MovieContract.VideoEntry.COLUMN_ISO_639_1, "en");
        testValues.put(MovieContract.VideoEntry.COLUMN_KEY, "bvu-zlR5A8Q");
        testValues.put(MovieContract.VideoEntry.COLUMN_NAME, "Teaser");
        testValues.put(MovieContract.VideoEntry.COLUMN_SITE, "YouTube");
        testValues.put(MovieContract.VideoEntry.COLUMN_SIZE, "1080");
        testValues.put(MovieContract.VideoEntry.COLUMN_TYPE, "Teaser");

        return testValues;
    }


    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            Log.v(LOG_TAG, "validateCurrentRecord(): columnName = " + columnName + ", value = " + entry.getValue().toString() + ", " + valueCursor.getString(idx) + ", expectedValue = " + expectedValue);

            if (columnName == "adult" || columnName == "video") {
                assertTrue("Value " + entry.getValue().toString() +
                        "' did not match the expected value '" +
                        expectedValue + "'. " + error, valueCursor.getInt(idx) > 0);
            } else {
                assertEquals("Value '" + entry.getValue().toString() +
                        "' did not match the expected value '" +
                        expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
            }
        }
    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

}
