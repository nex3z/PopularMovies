package com.nex3z.popularmovies.presentation.view;

import android.content.Context;

import com.nex3z.popularmovies.domain.model.movie.MovieModel;

import java.util.List;

public interface MovieListView extends BaseView, LoadDataView {

    void renderMovies(List<MovieModel> movies);

    void renderMovies(List<MovieModel> movies, int start, int count);

    void showDetail(MovieModel movie);

    Context getContext();

}
