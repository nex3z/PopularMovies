package com.nex3z.popularmovies.presentation.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nex3z.popularmovies.BuildConfig;
import com.nex3z.popularmovies.data.net.RestClient;
import com.nex3z.popularmovies.data.repository.MovieRepository;
import com.nex3z.popularmovies.data.repository.MovieRepositoryImpl;
import com.nex3z.popularmovies.domain.PopMovieService;
import com.nex3z.popularmovies.domain.executor.JobExecutor;
import com.nex3z.popularmovies.presentation.UiThread;

public class App extends Application {

    private static Context sContext;
    private static PopMovieService sService;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        Fresco.initialize(this);
        initService();
    }

    private void initService() {
        RestClient restClient = new RestClient(BuildConfig.API_KEY);
        MovieRepository repository = new MovieRepositoryImpl(getApplicationContext(), restClient);
        sService = new PopMovieService(repository, new JobExecutor(), UiThread.getInstance());
    }

    public static Context getContext() {
        return sContext;
    }

    public static PopMovieService getPopMovieService() {
        return sService;
    }
}
