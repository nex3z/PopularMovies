package com.nex3z.popularmovies.presentation.presenter;

import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.view.MovieInfoView;

public class MovieInfoPresenter implements Presenter {
    private String LOG_TAG = MovieInfoPresenter.class.getSimpleName();

    private MovieModel mMovie;
    private MovieInfoView mView;

    public MovieInfoPresenter(MovieModel movie) {
        mMovie = movie;
    }

    public void setView(MovieInfoView view) {
        mView = view;
    }

    public void init() {
        mView.renderMovie(mMovie);
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {}

}
