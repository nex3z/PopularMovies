package com.nex3z.popularmovies.app;

import android.app.Application;

import com.nex3z.popularmovies.data.net.RestClient;

public class App extends Application {
    private static RestClient mRestClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mRestClient = new RestClient();
    }

    public static RestClient getRestClient() {
        return mRestClient;
    }
}
