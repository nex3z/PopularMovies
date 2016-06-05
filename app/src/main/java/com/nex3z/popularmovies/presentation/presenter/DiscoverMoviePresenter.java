package com.nex3z.popularmovies.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.domain.interactor.DefaultSubscriber;
import com.nex3z.popularmovies.domain.interactor.GetMovieListArg;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.presentation.mapper.MovieModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.ui.MovieGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DiscoverMoviePresenter implements Presenter {
    private static final String LOG_TAG = DiscoverMoviePresenter.class.getSimpleName();
    private static final int FIRST_PAGE= 1;

    private MovieGridView mMovieGridView;

    private final UseCase mGetMovieListUseCase;
    private final MovieModelDataMapper mMovieModelDataMapper;
    private List<MovieModel> mMovies = new ArrayList<>();
    private int mPage = FIRST_PAGE;
    private String mSortBy = GetMovieListArg.SORT_BY_POPULARITY_DESC;

    public DiscoverMoviePresenter(UseCase getMovieListUseCase,
                                  MovieModelDataMapper movieModelDataMapper) {
        mGetMovieListUseCase = getMovieListUseCase;
        mMovieModelDataMapper = movieModelDataMapper;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mGetMovieListUseCase.unsubscribe();
    }

    public void setView(@NonNull MovieGridView view) {
        mMovieGridView = view;
    }

    public void initialize() {
        hideViewRetry();
        showViewLoading();
        refresh();
    }

    public void refresh() {
        mGetMovieListUseCase.unsubscribe();
        mPage = FIRST_PAGE;
        mMovies.clear();
        mMovieGridView.renderMovieList(mMovies);

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

    public MovieModel onMovieSelected(int position) {
        return mMovies.get(position);
    }

    private void showViewLoading() {
        mMovieGridView.showLoading();
    }

    private void hideViewLoading() {
        mMovieGridView.hideLoading();
    }

    private void showViewRetry() {
        mMovieGridView.showRetry();
    }

    private void hideViewRetry() {
        mMovieGridView.hideRetry();
    }

    private void showErrorMessage(String message) {
        mMovieGridView.showError(message);
    }

    private void showMovieCollectionInView(Collection<Movie> movieCollection) {
        int currentSize = mMovies.size();
        mMovies.addAll(mMovieModelDataMapper.transform(movieCollection));
        Log.v(LOG_TAG, "showMovieCollectionInView(): movieCollection = "
                + movieCollection.size() + ", mMovies = " + mMovies.size());

        if (currentSize == 0) {
            mMovieGridView.renderMovieList(mMovies);
        } else {
            mMovieGridView.renderMovieList(mMovies, currentSize, movieCollection.size());
        }
    }

    @SuppressWarnings("unchecked")
    private void fetchMovies() {
        Log.v(LOG_TAG, "fetchMovies(): mPage = " + mPage + ", mSortBy = " + mSortBy);
        mGetMovieListUseCase.init(new GetMovieListArg(mSortBy, mPage))
                .execute(new MovieListSubscriber());
    }

    private final class MovieListSubscriber extends DefaultSubscriber<List<Movie>> {

        @Override public void onCompleted() {
            hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            hideViewLoading();
            showErrorMessage(e.getMessage());
            Log.e(LOG_TAG, "onError(): e = " + e.getMessage());
            showViewRetry();
        }

        @Override public void onNext(List<Movie> movies) {
            showMovieCollectionInView(movies);
        }
    }

}
