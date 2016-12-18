package com.nex3z.popularmovies.presentation.internal.di.component;

import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.internal.di.module.ActivityModule;
import com.nex3z.popularmovies.presentation.internal.di.module.VideoModule;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieVideoFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class,
        modules = {ActivityModule.class, VideoModule.class})
public interface VideoComponent {
    void inject(MovieVideoFragment movieVideoFragment);
}
