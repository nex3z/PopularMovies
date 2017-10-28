package com.nex3z.popularmovies.presentation.detail.info;

import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.base.BasePresenter;

public class MovieInfoPresenter extends BasePresenter<MovieInfoView> {

    private final MovieModel mMovie;

    public MovieInfoPresenter(MovieModel movie) {
        mMovie = movie;
    }

    public void init() {
        mView.renderMovie(mMovie);
    }

}
