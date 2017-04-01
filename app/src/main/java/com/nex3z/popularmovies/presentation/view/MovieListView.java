package com.nex3z.popularmovies.presentation.view;

import android.content.Context;

import com.nex3z.popularmovies.domain.model.movie.MovieModel;

import java.util.Collection;

public interface MovieListView extends BaseView, LoadDataView {

    void renderMovieList(Collection<MovieModel> movieModels);

    void renderMovieList(Collection<MovieModel> movieModels, int start, int count);

    Context getContext();

}
