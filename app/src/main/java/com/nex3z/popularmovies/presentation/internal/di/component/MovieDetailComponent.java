package com.nex3z.popularmovies.presentation.internal.di.component;

import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.internal.di.module.ActivityModule;
import com.nex3z.popularmovies.presentation.internal.di.module.MovieDetailModule;
import com.nex3z.popularmovies.presentation.internal.di.module.MovieModule;
import com.nex3z.popularmovies.presentation.internal.di.module.ReviewModule;
import com.nex3z.popularmovies.presentation.internal.di.module.VideoModule;
import com.nex3z.popularmovies.presentation.ui.activity.MovieDetailActivity;
import com.nex3z.popularmovies.presentation.ui.fragment.IntegratedMovieInfoFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieInfoFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieReviewFragment;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieVideoFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class,
        modules = {ActivityModule.class, MovieModule.class, VideoModule.class, ReviewModule.class,
                MovieDetailModule.class})
public interface MovieDetailComponent {
    void inject(MovieDetailActivity movieDetailActivity);
    void inject(MovieInfoFragment movieInfoFragment);
    void inject(MovieVideoFragment movieVideoFragment);
    void inject(MovieReviewFragment movieReviewFragment);
    void inject(IntegratedMovieInfoFragment integratedMovieInfoFragment);
}
