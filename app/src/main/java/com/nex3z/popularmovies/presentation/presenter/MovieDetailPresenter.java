package com.nex3z.popularmovies.presentation.presenter;


import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.ui.MovieDetailView;

public class MovieDetailPresenter implements Presenter{
    private static final String LOG_TAG = MovieDetailPresenter.class.getSimpleName();

    private MovieDetailView mView;
    private MovieModel mMovieModel;

    public void setView(MovieDetailView view) {
        mView = view;
    }

    public void setMovie(MovieModel movie) {
        mMovieModel = movie;
    }

    public void initialize() {
        mView.renderMovie(mMovieModel);
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {}
}
