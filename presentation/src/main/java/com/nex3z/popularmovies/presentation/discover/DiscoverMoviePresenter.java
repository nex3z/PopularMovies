package com.nex3z.popularmovies.presentation.discover;

import android.util.Log;

import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieParams;
import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.DiscoverMoviesUseCase;
import com.nex3z.popularmovies.domain.interactor.SetMovieFavouriteUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.app.App;
import com.nex3z.popularmovies.presentation.base.BasePresenter;

import java.util.Collections;
import java.util.List;

public class DiscoverMoviePresenter extends BasePresenter<DiscoverMovieView> {
    private static final String LOG_TAG = DiscoverMoviePresenter.class.getSimpleName();
    private static final int FIRST_PAGE = 1;

    private final DiscoverMoviesUseCase mDiscoverMoviesUseCase;
    private final SetMovieFavouriteUseCase mSetMovieFavouriteUseCase;
    private List<MovieModel> mMovies;
    private int mPage = 1;

    public DiscoverMoviePresenter() {
        mDiscoverMoviesUseCase = App.getPopMovieService().create(DiscoverMoviesUseCase.class);
        mSetMovieFavouriteUseCase = App.getPopMovieService().create(SetMovieFavouriteUseCase.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        mDiscoverMoviesUseCase.dispose();
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
        mView.renderMovies(Collections.emptyList());
        fetchMovies(FIRST_PAGE);
    }

    public void loadMoreMovie() {
        fetchMovies(mPage + 1);
    }

    public void toggleFavourite(int position) {
        MovieModel movie = mMovies.get(position);
        mSetMovieFavouriteUseCase.execute(new SetFavouriteObserver(movie, position),
                SetMovieFavouriteUseCase.Params.forMovie(movie, !movie.isFavourite()));
    }

    private void fetchMovies(int page) {
        mDiscoverMoviesUseCase.execute(new MovieObserver(page),
                new DiscoverMovieParams.Builder()
                        .sortBy(DiscoverMovieParams.SORT_BY_POPULARITY_DESC)
                        .page(page)
                        .build()
        );
    }

    private class MovieObserver extends DefaultObserver<List<MovieModel>> {
        private final int mPage;

        MovieObserver(int page) {
            mPage = page;
        }

        @Override
        protected void onStart() {
            super.onStart();
            mView.showLoading();
        }

        @Override
        public void onNext(List<MovieModel> movies) {
            super.onNext(movies);
            Log.v(LOG_TAG, "onNext(): mPage = " + mPage + ", size = " + movies.size());

            if (mPage == FIRST_PAGE) {
                mMovies = movies;
                mView.renderMovies(mMovies);
            } else {
                int start = mMovies.size();
                mMovies.addAll(movies);
                mView.notifyMovieInserted(start, movies.size());
            }

            mView.hideLoading();
            DiscoverMoviePresenter.this.mPage = mPage;
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
