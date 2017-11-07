package com.nex3z.popularmovies.presentation.favourite;

import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.GetFavouriteMoviesUseCase;
import com.nex3z.popularmovies.domain.interactor.SetMovieFavouriteUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.app.App;
import com.nex3z.popularmovies.presentation.base.BasePresenter;

import java.util.List;

public class FavouriteMoviePresenter extends BasePresenter<FavouriteMovieView> {
    private static final String LOG_TAG = FavouriteMoviePresenter.class.getSimpleName();

    private final GetFavouriteMoviesUseCase mGetFavouriteMoviesUseCase;
    private final SetMovieFavouriteUseCase mSetMovieFavouriteUseCase;
    private List<MovieModel> mMovies;

    public FavouriteMoviePresenter() {
        mGetFavouriteMoviesUseCase = App.getPopMovieService()
                .create(GetFavouriteMoviesUseCase.class);
        mSetMovieFavouriteUseCase = App.getPopMovieService()
                .create(SetMovieFavouriteUseCase.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        mGetFavouriteMoviesUseCase.dispose();
        mSetMovieFavouriteUseCase.dispose();
    }

    public void init() {
        refreshMovie();
    }

    public void onMovieClicked(int position) {
        if (position >= 0 && position < mMovies.size()) {
            mView.renderMovieDetail(mMovies.get(position));
        }
    }

    public void refreshMovie() {
        fetchMovies();
    }

    public void toggleFavourite(int position) {
        MovieModel movie = mMovies.get(position);
        mSetMovieFavouriteUseCase.execute(new SetFavouriteObserver(movie, position),
                SetMovieFavouriteUseCase.Params.forMovie(movie, !movie.isFavourite()));
    }

    private void fetchMovies() {
        mGetFavouriteMoviesUseCase.execute(new MovieObserver(), null);
    }

    private class MovieObserver extends DefaultObserver<List<MovieModel>> {
        @Override
        protected void onStart() {
            super.onStart();
            mView.showLoading();
        }

        @Override
        public void onNext(List<MovieModel> movies) {
            super.onNext(movies);
            mMovies = movies;
            mView.renderMovies(mMovies);
            mView.hideLoading();
        }

        @Override
        public void onError(Throwable throwable) {
            super.onError(throwable);
            mView.hideLoading();
            mView.showError(throwable.getMessage());
        }
    }

    private class SetFavouriteObserver extends DefaultObserver<Boolean> {
        private final MovieModel mMovie;
        private final int mPosition;

        SetFavouriteObserver(MovieModel movie, int position) {
            mMovie = movie;
            mPosition = position;
        }

        @Override
        public void onNext(Boolean favourite) {
            super.onNext(favourite);
            mMovie.setFavourite(favourite);
            if (mMovies != null && mMovies.size() > mPosition
                    && mMovies.get(mPosition).getId() == mMovie.getId()) {
                mView.notifyMovieChanged(mPosition);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            super.onError(throwable);
            mView.showError(throwable.getMessage());
        }
    }
}
