package com.nex3z.popularmovies.presentation.internal.di.module;

import android.app.Activity;

import com.nex3z.popularmovies.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity
    Activity activity() {
        return this.activity;
    }
}
