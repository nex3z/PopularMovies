package com.nex3z.popularmovies.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;


public class MovieService extends IntentService {

    private static final String LOG_TAG = MovieService.class.getSimpleName();

    private static final String ACTION_FETCH_NEW_MOVIES = "com.nex3z.movie.service.action.FETCH_NEW_MOVIES";

    private static final String EXTRA_SORT_ORDER = "com.nex3z.movie.service.extra.PARAM1";

    public static void startActionFetchNewMovies(Context context, String param1) {
        Intent intent = new Intent(context, MovieService.class);
        intent.setAction(ACTION_FETCH_NEW_MOVIES);
        intent.putExtra(EXTRA_SORT_ORDER, param1);
        context.startService(intent);
    }

    public MovieService() {
        super("MovieService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG_TAG, "onHandleIntent()");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_NEW_MOVIES.equals(action)) {
                Log.v(LOG_TAG, "onHandleIntent(): Handle ACTION_FETCH_NEW_MOVIES");
                final String sortOrder = intent.getStringExtra(EXTRA_SORT_ORDER);
                handleActionFetchNewMovies(sortOrder);
            }
        }
    }

    private void handleActionFetchNewMovies(String param) {
        Log.v(LOG_TAG, "handleActionFetchNewMovies(): param = " + param);
        fetchNewMovies();
    }

    private void fetchNewMovies() {
        Log.v(LOG_TAG, "fetchNewMovies()");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;
        String sort = "popularity.desc";

        try {
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, sort)
                    .appendQueryParameter(API_KEY_PARAM, this.getString(R.string.api_key))
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "fetchNewMovies(): URL = " + url);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return;
            }
            movieJsonStr = buffer.toString();
            Log.v(LOG_TAG, "fetchNewMovies(): buffer = " + buffer);
            getMovieFromJson(movieJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    private void getMovieFromJson(String movieJsonStr) throws JSONException {
        Log.v(LOG_TAG, "getMovieFromJson(): getMovieFromJson = " + movieJsonStr);

        final String TMDB_PAGE = "page";
        final String TMDB_RESULTS = "results";
        final String TMDB_ADULT = "adult";
        final String TMDB_BACKDROP_PATH = "backdrop_path";
        final String TMDB_GENRE_IDS = "genre_ids";
        final String TMDB_ID = "id";
        final String TMDB_ORIGINAL_LANGUAGE = "original_language";
        final String TMDB_ORIGINAL_TITLE = "original_title";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_POPULARITY = "popularity";
        final String TMDB_TITLE = "title";
        final String TMDB_VIDEO = "video";
        final String TMDB_VOTE_AVERAGE = "vote_average";
        final String TMDB_VOTE_COUNT = "vote_count";
        final String TMDB_TOTAL_PAGES = "total_pages";
        final String TMDB_TOTAL_RESULTS = "total_results";

        try {
            JSONObject forecastJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = forecastJson.getJSONArray(TMDB_RESULTS);
            Log.v(LOG_TAG, "getMovieFromJson(): movieArray.length = " + movieArray.length());
            Log.v(LOG_TAG, "getMovieFromJson(): movieArray = " + movieArray);

            Vector<ContentValues> cVVector = new Vector<ContentValues>(movieArray.length());

            this.getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    null
            );

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);

                Boolean adult = movie.getBoolean(TMDB_ADULT);
                String backdropPath = movie.getString(TMDB_BACKDROP_PATH);
                long id = movie.getLong(TMDB_ID);
                String originalLanguage = movie.getString(TMDB_ORIGINAL_LANGUAGE);
                String originalTitle = movie.getString(TMDB_ORIGINAL_TITLE);
                String overview = movie.getString(TMDB_OVERVIEW);
                String releaseDate = movie.getString(TMDB_RELEASE_DATE);
                String posterPath = movie.getString(TMDB_POSTER_PATH);
                double popularity = movie.getDouble(TMDB_POPULARITY);
                String title = movie.getString(TMDB_TITLE);
                Boolean video = movie.getBoolean(TMDB_VIDEO);
                double voteAverage = movie.getDouble(TMDB_VOTE_AVERAGE);
                int voteCount = movie.getInt(TMDB_VOTE_COUNT);
                String genreIds = movie.getString(TMDB_GENRE_IDS);

                ContentValues movieValues = new ContentValues();

                movieValues.put(MovieContract.MovieEntry.COLUMN_ID, id);
                movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, title);
                movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, voteCount);
                movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, voteAverage);
                movieValues.put(MovieContract.MovieEntry.COLUMN_ADULT, adult);
                movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, backdropPath);
                movieValues.put(MovieContract.MovieEntry.COLUMN_GENRE_IDS, genreIds);
                movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, originalLanguage);
                movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle);
                movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview);
                movieValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, popularity);
                movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, posterPath);
                movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate);
                movieValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, video);

                cVVector.add(movieValues);
            }

            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                this.getContentResolver()
                        .bulkInsert(MovieContract.MovieEntry.CONTENT_URI, cvArray);
            }
            Log.d(LOG_TAG, "Write database Complete. " + cVVector.size() + " Inserted");
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}


