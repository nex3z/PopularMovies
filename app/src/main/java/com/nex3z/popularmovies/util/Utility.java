package com.nex3z.popularmovies.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nex3z.popularmovies.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utility {

    public static final String LOG_TAG = Utility.class.getSimpleName();

    public static String fetchNewMovies(Context context) {
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
                    .appendQueryParameter(API_KEY_PARAM, context.getString(R.string.api_key))
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "fetchNewMovies(): URL = " + url);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Log.v(LOG_TAG, "handleActionFetchNewMovies(): line = " + line);
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.v(LOG_TAG, "fetchNewMovies(): buffer = " + buffer);
            return movieJsonStr;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, e.getMessage(), e);
//            e.printStackTrace();
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
        return null;
    }

    public static String[] convertPosterImagePath(String[] path) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String SIZE = "w185";

        String[] imageUrls = new String[path.length];
        for (int i = 0; i < path.length; i++) {
            imageUrls[i] = BASE_URL + SIZE + "/" + path[i];
            Log.v(LOG_TAG, "convertPosterImagePath(): " + imageUrls[i]);
        }

        return imageUrls;
    }

    public static String convertPosterImagePath(String path) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String SIZE = "w342";

        String imageUrls = new String();

        imageUrls = BASE_URL + SIZE + "/" + path;
        Log.v(LOG_TAG, "convertPosterImagePath(): " + imageUrls);


        return imageUrls;
    }

    public static String getPreferredSortOrder(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_sort_key),
                context.getString(R.string.pref_sort_popularity));
    }

}
