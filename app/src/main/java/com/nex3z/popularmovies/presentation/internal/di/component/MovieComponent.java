package com.nex3z.popularmovies.presentation.internal.di.component;

import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.internal.di.module.ActivityModule;
import com.nex3z.popularmovies.presentation.internal.di.module.MovieModule;
import com.nex3z.popularmovies.presentation.ui.activity.MovieGridActivity;
import com.nex3z.popularmovies.presentation.ui.fragment.MovieGridFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class,
        modules = {ActivityModule.class, MovieModule.class})
public interface MovieComponent {
    void inject(MovieGridActivity movieGridActivity);
    void inject(MovieGridFragment movieGridFragment);
}
