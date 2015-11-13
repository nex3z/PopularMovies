package com.nex3z.popularmovies.data.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(MovieContract.MovieEntry.TABLE_NAME);

        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MovieDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        do {
            Log.v(LOG_TAG, "testCreateDb(): c.getString(0) = " + c.getString(0));
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + MovieContract.MovieEntry.TABLE_NAME + ")", null);
        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        final HashSet<String> movieColumnHashSet = new HashSet<String>();
        movieColumnHashSet.add(MovieContract.MovieEntry._ID);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_ADULT);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_GENRE_IDS);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_ID);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_POPULARITY);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_TITLE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_VIDEO);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE);
        movieColumnHashSet.add(MovieContract.MovieEntry.COLUMN_VOTE_COUNT);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            movieColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                movieColumnHashSet.isEmpty());
        db.close();
    }

    public void testMovieTable() throws Throwable {
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createMovieValues();

        long movieRowId;
        movieRowId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, testValues);
        Log.d(LOG_TAG, "testMovieTable(): movieRowId = " + movieRowId);
        assertTrue(movieRowId != -1);

        Cursor cursor = db.query(
                MovieContract.MovieEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        Log.d(LOG_TAG, "testMovieTable(): cursor = " + cursor);

        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());

        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",
                cursor, testValues);

        assertFalse("Error: More than one record returned from location query",
                cursor.moveToNext());
        
        cursor.close();
        db.close();
    }

    public void testCreateVideoDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(MovieContract.VideoEntry.TABLE_NAME);

        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MovieDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        do {
            Log.v(LOG_TAG, "testCreateVideoDb(): c.getString(0) = " + c.getString(0));
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + MovieContract.VideoEntry.TABLE_NAME + ")", null);
        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        final HashSet<String> movieColumnHashSet = new HashSet<String>();
        movieColumnHashSet.add(MovieContract.VideoEntry._ID);
        movieColumnHashSet.add(MovieContract.VideoEntry.COLUMN_ISO_639_1);
        movieColumnHashSet.add(MovieContract.VideoEntry.COLUMN_KEY);
        movieColumnHashSet.add(MovieContract.VideoEntry.COLUMN_MOVIE_ID);
        movieColumnHashSet.add(MovieContract.VideoEntry.COLUMN_NAME);
        movieColumnHashSet.add(MovieContract.VideoEntry.COLUMN_SITE);
        movieColumnHashSet.add(MovieContract.VideoEntry.COLUMN_SIZE);
        movieColumnHashSet.add(MovieContract.VideoEntry.COLUMN_TYPE);
        movieColumnHashSet.add(MovieContract.VideoEntry.COLUMN_VIDEO_ID);

        int columnNameIndex = c.getColumnIndex("name");
        Log.v(LOG_TAG, "testCreateVideoDb(): columnNameIndex = " + columnNameIndex);

        do {
            String columnName = c.getString(columnNameIndex);
            Log.v(LOG_TAG, "testCreateVideoDb(): columnName = " + columnName);
            movieColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                movieColumnHashSet.isEmpty());
        db.close();
    }

    public void testVideoTable() throws Throwable {
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createVideoValues();

        long videoRowId;
        videoRowId = db.insert(MovieContract.VideoEntry.TABLE_NAME, null, testValues);
        Log.d(LOG_TAG, "testVideoTable(): videoRowId = " + videoRowId);
        assertTrue(videoRowId != -1);

        Cursor cursor = db.query(
                MovieContract.VideoEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );
        Log.d(LOG_TAG, "testVideoTable(): cursor = " + cursor);

        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());

        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",
                cursor, testValues);

        assertFalse("Error: More than one record returned from location query",
                cursor.moveToNext());

        cursor.close();
        db.close();
    }

    public void testVideoTableInsertConflict() throws Throwable {
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createVideoValues();

        long videoRowId;
        videoRowId = db.insert(MovieContract.VideoEntry.TABLE_NAME, null, testValues);
        Log.d(LOG_TAG, "testVideoTableInsertConflict(): videoRowId = " + videoRowId);
        assertTrue(videoRowId != -1);

        Cursor cursor = db.query(
                MovieContract.VideoEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());

        Log.d(LOG_TAG, "testVideoTableInsertConflict(): cursor = " + cursor + "movie id = "
                + cursor.getLong(cursor.getColumnIndex(MovieContract.VideoEntry.COLUMN_MOVIE_ID)));
        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",
                cursor, testValues);

        assertFalse("Error: More than one record returned from location query",
                cursor.moveToNext());


        ContentValues conflictValues = TestUtilities.createVideoValues(9527);
        videoRowId = db.insert(MovieContract.VideoEntry.TABLE_NAME, null, conflictValues);
        Log.d(LOG_TAG, "testVideoTableInsertConflict(): videoRowId = " + videoRowId);
        assertTrue(videoRowId != -1);

        cursor = db.query(
                MovieContract.VideoEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        assertTrue("Error: No Records returned from location query", cursor.moveToFirst());

        Log.d(LOG_TAG, "testVideoTableInsertConflict(): cursor = " + cursor + "movie id = "
                + cursor.getLong(cursor.getColumnIndex(MovieContract.VideoEntry.COLUMN_MOVIE_ID)));
        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed",
                cursor, conflictValues);

        assertFalse("Error: More than one record returned from location query",
                cursor.moveToNext());

        cursor.close();
        db.close();
    }

}
