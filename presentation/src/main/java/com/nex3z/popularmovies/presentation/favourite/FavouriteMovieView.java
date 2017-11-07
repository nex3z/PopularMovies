package com.nex3z.popularmovies.presentation.favourite;

import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.base.BaseView;
import com.nex3z.popularmovies.presentation.base.LoadDataView;

import java.util.List;

public interface FavouriteMovieView extends BaseView, LoadDataView {

    void renderMovies(List<MovieModel> movies);

    void notifyMovieChanged(int position);

    void renderMovieDetail(MovieModel movie);

}
