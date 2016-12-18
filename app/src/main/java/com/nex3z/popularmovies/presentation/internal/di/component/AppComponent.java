package com.nex3z.popularmovies.presentation.internal.di.component;

import android.content.Context;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.repository.MovieRepository;
import com.nex3z.popularmovies.domain.repository.ReviewRepository;
import com.nex3z.popularmovies.domain.repository.VideoRepository;
import com.nex3z.popularmovies.presentation.internal.di.module.AppModule;
import com.nex3z.popularmovies.presentation.ui.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(BaseActivity activity);

    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    MovieRepository movieRepository();
    VideoRepository videoRepository();
    ReviewRepository reviewRepository();
}
