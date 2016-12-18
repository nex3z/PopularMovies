package com.nex3z.popularmovies.app;

import android.app.Application;
import android.content.Context;

import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.presentation.internal.di.component.AppComponent;
import com.nex3z.popularmovies.presentation.internal.di.component.DaggerAppComponent;
import com.nex3z.popularmovies.presentation.internal.di.module.AppModule;

public class App extends Application {
    private static Context mContext;
    private static RestClient mRestClient;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mRestClient = new RestClient();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static RestClient getRestClient() {
        return mRestClient;
    }
}
