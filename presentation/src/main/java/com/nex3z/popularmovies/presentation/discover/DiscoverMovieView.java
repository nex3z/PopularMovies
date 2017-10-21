package com.nex3z.popularmovies.presentation.discover;

import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.base.BaseView;
import com.nex3z.popularmovies.presentation.base.LoadDataView;

import java.util.List;

public interface DiscoverMovieView extends BaseView, LoadDataView {

    void renderMovies(List<MovieModel> movies);

    void notifyMovieInserted(int position, int count);

    void renderMovieDetail(MovieModel movie);

}
