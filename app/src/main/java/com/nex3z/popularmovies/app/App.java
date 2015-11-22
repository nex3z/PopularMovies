package com.nex3z.popularmovies.app;

import android.app.Application;
import android.util.Log;

import com.nex3z.popularmovies.data.rest.RestClient;

public class App extends Application{
    private static final String LOG_TAG = App.class.getSimpleName();

    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();

        restClient = new RestClient();
        Log.v(LOG_TAG, "onCreate(): restClient = " + restClient);
    }

    public static RestClient getRestClient() {
        return restClient;
    }
}
