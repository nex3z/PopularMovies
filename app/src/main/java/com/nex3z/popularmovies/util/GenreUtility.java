package com.nex3z.popularmovies.util;


import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.nex3z.popularmovies.R;

public class GenreUtility {

    public static final String LOG_TAG = GenreUtility.class.getSimpleName();

    private static SparseArray<String> mGenreMap = new SparseArray<String>();
    private static boolean isGenreMapGenerated = false;

    public static void buildGenreMap(Context context) {
        String[] countriesNames = context.getResources().getStringArray(R.array.genre_names);
        int[] countriesCodes = context.getResources().getIntArray(R.array.genre_ids);
        Log.v(LOG_TAG, "buildGenreMap(): countriesNames length = " + countriesNames.length
                + "countriesCodes length = " + countriesCodes.length);

        for (int i = 0; i < countriesNames.length; i++) {
            mGenreMap.put(countriesCodes[i], countriesNames[i]);
        }
        isGenreMapGenerated = true;
    }

    public static String getGenreName(int id) {
        return mGenreMap.get(id, "");
    }
}
