package com.nex3z.popularmovies.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.movie.DiscoverMovieUseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.view.MovieListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieListPresenter implements Presenter {
    private static final String LOG_TAG = MovieListPresenter.class.getSimpleName();
    private static final int FIRST_PAGE= 1;

    private MovieListView mView;

    private final DiscoverMovieUseCase mDiscoverMovieUseCase;
    private final List<MovieModel> mMovies = new ArrayList<>();
    private int mPage = FIRST_PAGE;
    private String mSortBy = DiscoverMovieUseCase.Params.SORT_BY_POPULARITY_DESC;

    public MovieListPresenter(DiscoverMovieUseCase discoverMovieUseCase) {
        mDiscoverMovieUseCase = discoverMovieUseCase;
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

    public void initialize() {
        mView.showLoading();
        refresh();
    }

    public void refresh() {
        mPage = FIRST_PAGE;
        mMovies.clear();
        mView.renderMovieList(mMovies);
        fetchMovies();
    }

    public void setPage(int page) {
        mPage = page;
    }

    public void setSortBy(String sortBy) {
        mSortBy = sortBy;
    }

    public List<MovieModel> getMovies() {
        return mMovies;
    }

    public void loadMore(int page) {
        Log.v(LOG_TAG, "loadMore(): page = " + page);
        mPage = page;
        fetchMovies();
    }

    public MovieModel onMovieSelect(int position) {
        return mMovies.get(position);
    }

    private void showMovieCollectionInView(Collection<MovieModel> movies) {
        Log.v(LOG_TAG, "showMovieCollectionInView(): Showing " + movies.size()
                + " more movies, total = " + mMovies.size());

        int currentSize = mMovies.size();
        mMovies.addAll(movies);
        if (currentSize == 0) {
            mView.renderMovieList(mMovies);
        } else {
            mView.renderMovieList(mMovies, currentSize, movies.size());
        }
    }

    private void fetchMovies() {
        Log.v(LOG_TAG, "fetchMovies(): mPage = " + mPage + ", mSortBy = " + mSortBy);
        mDiscoverMovieUseCase.execute(new DiscoverMovieObserver(),
                DiscoverMovieUseCase.Params.forPage(mPage, mSortBy));
    }

    private final class DiscoverMovieObserver extends DefaultObserver<List<MovieModel>> {
        @Override
        public void onNext(List<MovieModel> movieModels) {
            mView.hideLoading();
            showMovieCollectionInView(movieModels);
        }

        @Override
        public void onComplete() {
            mView.hideLoading();
        }

        @Override
        public void onError(Throwable exception) {
            mView.hideLoading();
            mView.showError(exception.getMessage());
        }
    }

}
