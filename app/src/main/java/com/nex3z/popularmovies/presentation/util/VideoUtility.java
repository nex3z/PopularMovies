package com.nex3z.popularmovies.presentation.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.nex3z.popularmovies.domain.Video;

public class VideoUtility {
    public static final String LOG_TAG = VideoUtility.class.getSimpleName();

    public static void playVideo(Context context, String site, String key) {
        final String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";
        Log.v(LOG_TAG, "playVideo(): site = " + site + ", key = " + key);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = null;
        switch (site) {
            case Video.YOUTUBE: {
                uri = Uri.parse(YOUTUBE_BASE_URL + key);
                break;
            }
            default: {
                uri = Uri.parse(YOUTUBE_BASE_URL + key);
            }
        }

        intent.setData(uri);
        context.startActivity(intent);
    }
}
