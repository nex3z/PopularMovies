package com.nex3z.popularmovies.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.movie.DiscoverMovieUseCase;
import com.nex3z.popularmovies.domain.interactor.movie.GetFavouriteMovieUseCase;
import com.nex3z.popularmovies.domain.interactor.movie.SetFavouriteUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.view.MovieListView;

import java.util.ArrayList;
import java.util.List;

public class MovieListPresenter implements Presenter {
    private static final String LOG_TAG = MovieListPresenter.class.getSimpleName();
    private static final int FIRST_PAGE= 1;

    private MovieListView mView;

    private final UseCase mDiscoverMovieUseCase;
    private final SetFavouriteUseCase mAddToFavouriteUseCase;
    private final List<MovieModel> mMovies = new ArrayList<>();
    private int mPage = FIRST_PAGE;
    private String mSortBy = DiscoverMovieUseCase.Params.SORT_BY_POPULARITY_DESC;

    public MovieListPresenter(UseCase discoverMovieUseCase,
                              SetFavouriteUseCase addToFavouriteUseCase) {
        mDiscoverMovieUseCase = discoverMovieUseCase;
        mAddToFavouriteUseCase = addToFavouriteUseCase;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mDiscoverMovieUseCase.dispose();
        mView = null;
    }

    public void setView(@NonNull MovieListView view) {
        mView = view;
    }

    public void init() {
        mView.showLoading();
        refresh();
    }

    public void refresh() {
        mPage = FIRST_PAGE;
        mMovies.clear();
        mView.renderMovies(mMovies);
        fetchMovies();
    }

    public void setSortBy(String sortBy) {
        mSortBy = sortBy;
    }

    public List<MovieModel> getMovies() {
        return mMovies;
    }

    public void loadMore() {
        if (mDiscoverMovieUseCase instanceof GetFavouriteMovieUseCase) {
            mView.hideLoading();
            return;
        }

        Log.v(LOG_TAG, "loadMore(): mPage = " + mPage);
        mPage++;
        fetchMovies();
    }

    public void onMovieSelect(int position) {
        MovieModel movie = mMovies.get(position);
        mView.showDetail(movie);
    }

    private void renderMovies(List<MovieModel> movies) {
        Log.v(LOG_TAG, "renderMovies(): Showing " + movies.size()
                + " more movies, total = " + mMovies.size());

        int currentSize = mMovies.size();
        mMovies.addAll(movies);
        if (currentSize == 0) {
            mView.renderMovies(mMovies);
        } else {
            mView.renderMovies(mMovies, currentSize, movies.size());
        }
    }

    @SuppressWarnings("unchecked")
    private void fetchMovies() {
        Log.v(LOG_TAG, "fetchMovies(): mPage = " + mPage + ", mSortBy = " + mSortBy);
        if (mDiscoverMovieUseCase instanceof DiscoverMovieUseCase) {
            mDiscoverMovieUseCase.execute(new DiscoverMovieObserver(),
                    DiscoverMovieUseCase.Params.forPage(mPage, mSortBy));
        } else if (mDiscoverMovieUseCase instanceof GetFavouriteMovieUseCase) {
            mDiscoverMovieUseCase.execute(new DiscoverMovieObserver(), null);
        }
    }

    public void swapFavourite(int position) {
        MovieModel movie = mMovies.get(position);
        Log.v(LOG_TAG, "swapFavourite(): position = " + position + ", movie = " + movie);
        mAddToFavouriteUseCase.execute(new SetFavouriteObserver(position),
                SetFavouriteUseCase.Params.forMovie(movie, !movie.isFavourite()));
    }

    private void updateFavourite(int position, boolean isFavourite) {
        MovieModel movie = mMovies.get(position);
        movie.setFavourite(isFavourite);
        mView.updateMovie(position);
    }

    private final class DiscoverMovieObserver extends DefaultObserver<List<MovieModel>> {
        @Override
        public void onNext(List<MovieModel> movieModels) {
            mView.hideLoading();
            if (!movieModels.isEmpty()) {
                renderMovies(movieModels);
            } else {
                mPage--;
            }
        }

        @Override
        public void onError(Throwable exception) {
            mView.hideLoading();
            mView.showError(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private final class SetFavouriteObserver extends DefaultObserver<Boolean> {
        private int mPosition;

        SetFavouriteObserver(int position) {
            mPosition = position;
        }

        @Override
        public void onNext(Boolean b) {
            Log.v(LOG_TAG, "SetFavouriteObserver onNext(): " + b);
            updateFavourite(mPosition, b);
        }

        @Override
        public void onError(Throwable exception) {
            Log.v(LOG_TAG, "SetFavouriteObserver onError(): " + exception.getMessage());
            exception.printStackTrace();
        }
    }

}
