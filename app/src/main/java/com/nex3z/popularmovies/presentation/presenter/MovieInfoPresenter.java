package com.nex3z.popularmovies.presentation.presenter;


import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.ui.MovieInfoView;

public class MovieInfoPresenter implements Presenter {
    private static final String LOG_TAG = MovieInfoPresenter.class.getSimpleName();

    private MovieInfoView mView;
    private MovieModel mMovieModel;

    public MovieInfoPresenter(MovieModel movieModel) {
        mMovieModel = movieModel;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mView = null;
    }

    public void setView(MovieInfoView view) {
        mView = view;
    }

    public void initialize() {
        mView.renderMovie(mMovieModel);
    }

}
