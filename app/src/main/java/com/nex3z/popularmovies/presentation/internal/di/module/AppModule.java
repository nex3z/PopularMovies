package com.nex3z.popularmovies.presentation.internal.di.module;

import android.content.Context;

import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.executor.JobExecutor;
import com.nex3z.popularmovies.data.repository.MovieDataRepository;
import com.nex3z.popularmovies.data.repository.ReviewDataRepository;
import com.nex3z.popularmovies.data.repository.VideoDataRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.repository.MovieRepository;
import com.nex3z.popularmovies.domain.repository.ReviewRepository;
import com.nex3z.popularmovies.domain.repository.VideoRepository;
import com.nex3z.popularmovies.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return mApp;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    MovieRepository provideMovieRepository(MovieDataRepository movieDataRepository) {
        return movieDataRepository;
    }

    @Provides @Singleton
    VideoRepository provideVideoRepository(VideoDataRepository videoDataRepository) {
        return videoDataRepository;
    }

    @Provides @Singleton
    ReviewRepository provideReviewRepository(ReviewDataRepository reviewDataRepository) {
        return reviewDataRepository;
    }

}
