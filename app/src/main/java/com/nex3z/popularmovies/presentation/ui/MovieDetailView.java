package com.nex3z.popularmovies.presentation.ui;

import android.content.Context;

import com.nex3z.popularmovies.presentation.model.MovieModel;

public interface MovieDetailView {

    Context getContext();

    void renderMovie(MovieModel movie);

}
