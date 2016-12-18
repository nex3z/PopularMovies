package com.nex3z.popularmovies.presentation.internal.di.component;

import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.internal.di.module.ActivityModule;
import com.nex3z.popularmovies.presentation.internal.di.module.ReviewModule;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieReviewFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class,
        modules = {ActivityModule.class, ReviewModule.class})
public interface ReviewComponent {
    void inject(MovieReviewFragment movieReviewFragment);
}
