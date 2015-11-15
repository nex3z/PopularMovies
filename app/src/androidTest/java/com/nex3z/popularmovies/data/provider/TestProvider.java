package com.nex3z.popularmovies.data.provider;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.nex3z.popularmovies.data.provider.MovieContract.MovieEntry;
import com.nex3z.popularmovies.data.provider.MovieContract.VideoEntry;

public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                MovieEntry.CONTENT_URI,
                null,
                null
        );

        mContext.getContentResolver().delete(
                VideoEntry.CONTENT_URI,
                null,
                null
        );

        Cursor movieCursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Weather table during delete", 0, movieCursor.getCount());

        Cursor videoCursor = mContext.getContentResolver().query(
                VideoEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Weather table during delete", 0, videoCursor.getCount());

        movieCursor.close();
        videoCursor.close();
    }

    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MovieProvider.class.getName());
        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            assertEquals("Error: MovieProvider registered with authority: " +
                            providerInfo.authority +
                            " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    public void testGetType() {
        // content://com.nex3z.movie/movie
        String type = mContext.getContentResolver().getType(MovieEntry.CONTENT_URI);
        Log.v(LOG_TAG, "testGetType(): uri = " + MovieEntry.CONTENT_URI + ", type = " + type);
        // vnd.android.cursor.dir/com.nex3z.movie/movie
        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
                MovieEntry.CONTENT_TYPE, type);

        long testId = 9527L;
        // content://com.nex3z.movie/movie/9527
        type = mContext.getContentResolver().getType(MovieEntry.buildMovieUri(testId));
        Log.v(LOG_TAG, "testGetType(): uri = " + MovieEntry.buildMovieUri(testId) + ", type = " + type);
        // vnd.android.cursor.item/com.nex3z.movie/movie
        assertEquals("Error: the MovieEntry CONTENT_URI with location should return MovieEntry.CONTENT_TYPE",
                MovieEntry.CONTENT_ITEM_TYPE, type);
    }

    public void testBasicMovieQuery() {
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createMovieValues();

        long movieRowId = db.insert(MovieEntry.TABLE_NAME, null, testValues);
        Log.v(LOG_TAG, "testBasicMovieQuery(): movieRowId = " + String.valueOf(movieRowId));
        assertTrue("Unable to Insert MovieEntry into the Database", movieRowId != -1);

        db.close();

        Cursor movieCursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCursor("testBasicMovieQuery", movieCursor, testValues);
    }

    public void testUpdateMovie() {
        ContentValues values = TestUtilities.createMovieValues();

        Uri movieUri = mContext.getContentResolver().
                insert(MovieEntry.CONTENT_URI, values);
        long movieRowId = ContentUris.parseId(movieUri);
        Log.v(LOG_TAG, "testUpdateMovie(): movieRowId = " + String.valueOf(movieRowId));

        assertTrue(movieRowId != -1);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(MovieEntry._ID, movieRowId);
        updatedValues.put(MovieEntry.COLUMN_TITLE, "The Martian");

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor movieCursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, null, null, null, null);

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        movieCursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(
                MovieEntry.CONTENT_URI, updatedValues, MovieEntry._ID + "= ?",
                new String[] { Long.toString(movieRowId)});
        assertEquals(count, 1);

        tco.waitForNotificationOrFail();

        movieCursor.unregisterContentObserver(tco);
        movieCursor.close();

        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,   // projection
                MovieEntry._ID + " = " + movieRowId,
                null,   // Values for the "where" clause
                null    // sort order
        );

        TestUtilities.validateCursor("testUpdateLocation.  Error validating entry update.",
                cursor, updatedValues);

        cursor.close();
    }

    public void testUpdateVideo() {
        ContentValues values = TestUtilities.createVideoValues();

        Uri videoUri = mContext.getContentResolver().
                insert(VideoEntry.CONTENT_URI, values);
        long movieRowId = ContentUris.parseId(videoUri);
        Log.v(LOG_TAG, "testUpdateVideo(): videoUri = " + videoUri
                + ", movieRowId = " + String.valueOf(movieRowId));

        assertTrue(movieRowId != -1);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(VideoEntry._ID, movieRowId);
        updatedValues.put(VideoEntry.COLUMN_SITE, "404");

        Cursor movieCursor = mContext.getContentResolver().query(VideoEntry.CONTENT_URI, null, null, null, null);
        Log.v(LOG_TAG, "testUpdateVideo(): movieCursor =" + movieCursor);

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        movieCursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(
                VideoEntry.CONTENT_URI, updatedValues, VideoEntry._ID + "= ?",
                new String[] { Long.toString(movieRowId)});
        assertEquals(count, 1);

        tco.waitForNotificationOrFail();

        movieCursor.unregisterContentObserver(tco);
        movieCursor.close();

        Cursor cursor = mContext.getContentResolver().query(
                VideoEntry.CONTENT_URI,
                null,
                VideoEntry._ID + " = " + movieRowId,
                null,
                null
        );

        TestUtilities.validateCursor("testUpdateLocation.  Error validating entry update.",
                cursor, updatedValues);

        cursor.close();
    }

    public void testDeleteRecords() {
        testBasicMovieQuery();

        TestUtilities.TestContentObserver movieObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, movieObserver);

        deleteAllRecordsFromProvider();

        movieObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(movieObserver);
    }

    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;
    static ContentValues[] createBulkInsertWeatherValues() {
        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];
        String title = "Jurassic World";
        for ( int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++ ) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(MovieContract.MovieEntry.COLUMN_ADULT, true);
            movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, "/dkMD5qlogeRMiEixC4YNPUvax2T.jpg");
            movieValues.put(MovieContract.MovieEntry.COLUMN_GENRE_IDS, "[28,12,878,53]");
            movieValues.put(MovieContract.MovieEntry.COLUMN_ID, 135397 + i);
            movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, "en");
            movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, "Jurassic World");
            movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.");
            movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "2015-06-12");
            movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg");
            // testValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, 43.57727);
            movieValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, 43.577);
            movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Jurassic World" + " " + String.valueOf(i));
            movieValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, true);
            movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, 6.9);
            movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, 2735);
            returnContentValues[i] = movieValues;
        }
        return returnContentValues;
    }

    public void testBulkInsert() {
        ContentValues[] bulkInsertContentValues = createBulkInsertWeatherValues();

        TestUtilities.TestContentObserver weatherObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, weatherObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, bulkInsertContentValues);
        Log.v(LOG_TAG, "testBulkInsert(): insertCount = " + String.valueOf(insertCount));

        weatherObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(weatherObserver);

        assertEquals(insertCount, BULK_INSERT_RECORDS_TO_INSERT);

        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                MovieEntry.COLUMN_ID + " ASC"  // sort order == by ID ASCENDING
        );

        assertEquals(cursor.getCount(), BULK_INSERT_RECORDS_TO_INSERT);

        cursor.moveToFirst();
        for ( int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext() ) {
            TestUtilities.validateCurrentRecord("testBulkInsert.  Error validating WeatherEntry " + i,
                    cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }

    public void testBasicVideoQuery() {
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createVideoValues(1234, "abcd");
        ContentValues testValues2 = TestUtilities.createVideoValues(5678, "efgh");


        long movieRowId = db.insert(VideoEntry.TABLE_NAME, null, testValues);
        Log.v(LOG_TAG, "testBasicVideoQuery(): movieRowId = " + String.valueOf(movieRowId));
        assertTrue("Unable to Insert MovieEntry into the Database", movieRowId != -1);

        movieRowId = db.insert(VideoEntry.TABLE_NAME, null, testValues2);
        Log.v(LOG_TAG, "testBasicVideoQuery(): movieRowId = " + String.valueOf(movieRowId));
        assertTrue("Unable to Insert MovieEntry into the Database", movieRowId != -1);

        db.close();

        Uri uri = MovieContract.VideoEntry.CONTENT_URI.buildUpon().appendPath("5678").build();
        Log.v(LOG_TAG, "testBasicVideoQuery(): uri = " + uri);
        Cursor videoCursor = mContext.getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        Log.v(LOG_TAG, "testBasicVideoQuery(): videoCursor.getCount() = " + videoCursor.getCount());

        TestUtilities.validateCursor("testBasicMovieQuery", videoCursor, testValues2);
    }
}
