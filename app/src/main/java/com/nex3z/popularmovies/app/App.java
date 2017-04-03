package com.nex3z.popularmovies.app;

import android.app.Application;
import android.content.Context;

import com.nex3z.popularmovies.data.net.RestClient;

import io.realm.Realm;

public class App extends Application {
    private static Context mContext;
    private static RestClient sRestClient;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        sRestClient = new RestClient();
        Realm.init(this);
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static RestClient getRestClient() {
        return sRestClient;
    }
}
