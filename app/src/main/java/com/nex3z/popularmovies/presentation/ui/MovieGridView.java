package com.nex3z.popularmovies.presentation.ui;

import com.nex3z.popularmovies.presentation.model.MovieModel;

import java.util.Collection;

public interface MovieGridView extends LoadDataView {

    void renderMovieList(Collection<MovieModel> movieModelCollection);

    void renderMovieList(Collection<MovieModel> movieModelCollection, int start, int count);

}
