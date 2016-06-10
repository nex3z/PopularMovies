package com.nex3z.popularmovies.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.domain.interactor.DefaultSubscriber;
import com.nex3z.popularmovies.domain.interactor.movie.DeleteMovieArg;
import com.nex3z.popularmovies.domain.interactor.movie.GetFavouriteMovieList;
import com.nex3z.popularmovies.domain.interactor.movie.GetMovieList;
import com.nex3z.popularmovies.domain.interactor.movie.GetMovieListArg;
import com.nex3z.popularmovies.domain.interactor.movie.SaveMovieArg;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.presentation.mapper.MovieModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.ui.MovieGridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieListPresenter implements Presenter {
    private static final String LOG_TAG = MovieListPresenter.class.getSimpleName();
    private static final int FIRST_PAGE= 1;

    private MovieGridView mMovieGridView;

    private final UseCase mGetMovieListUseCase;
    private final UseCase mSaveMovieUseCase;
    private final UseCase mDeleteMovieUseCase;
    private final MovieModelDataMapper mMovieModelDataMapper;
    private List<MovieModel> mMovies = new ArrayList<>();
    private int mPage = FIRST_PAGE;
    private String mSortBy = GetMovieListArg.SORT_BY_POPULARITY_DESC;

    public MovieListPresenter(UseCase getMovieListUseCase,
                              UseCase saveMovieUseCase,
                              UseCase deleteMovieUseCase,
                              MovieModelDataMapper movieModelDataMapper) {
        mGetMovieListUseCase = getMovieListUseCase;
        mSaveMovieUseCase = saveMovieUseCase;
        mDeleteMovieUseCase = deleteMovieUseCase;
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

    public void favouriteStatusChanged(int position) {
        MovieModel movieModel = mMovies.get(position);
        if (movieModel != null) {
            boolean isFavourite = movieModel.isFavourite();
            Log.v(LOG_TAG, "favouriteStatusChanged(): position =" + position
                    + ", isFavourite = " + isFavourite);
            if (isFavourite) {
                // Not favourite anymore
                movieModel.setFavourite(false);
                deleteMovie(movieModel);
            } else {
                movieModel.setFavourite(true);
                saveMovie(movieModel);
            }
        }
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
        if (mGetMovieListUseCase instanceof GetMovieList) {
            Log.v(LOG_TAG, "fetchMovies(): mGetMovieListUseCase instanceof GetMovieList");
            mGetMovieListUseCase.init(new GetMovieListArg(mSortBy, mPage))
                    .execute(new MovieListSubscriber());
        } else if (mGetMovieListUseCase instanceof GetFavouriteMovieList) {
            Log.v(LOG_TAG, "fetchMovies(): mGetMovieListUseCase instanceof GetFavouriteMovieList");
            mGetMovieListUseCase.execute(new FavouriteMovieListSubscriber());
        }

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
            hideViewLoading();
            showMovieCollectionInView(movies);
        }
    }

    private final class FavouriteMovieListSubscriber extends DefaultSubscriber<List<Movie>> {

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
            hideViewLoading();
            mMovies.clear();
            showMovieCollectionInView(movies);
        }
    }

    @SuppressWarnings("unchecked")
    private void saveMovie(MovieModel movieModel) {
        Log.v(LOG_TAG, "saveMovie(): movieModel = " + movieModel);
        mSaveMovieUseCase.init(new SaveMovieArg(mMovieModelDataMapper.toMovie(movieModel)))
                .execute(new SaveMovieSubscriber());
    }

    private final class SaveMovieSubscriber extends DefaultSubscriber<Long> {
        @Override
        public void onNext(Long aLong) {
            Log.v(LOG_TAG, "onNext(): saved row id = " + aLong);
        }
    }

    @SuppressWarnings("unchecked")
    private void deleteMovie(MovieModel movieModel) {
        Log.v(LOG_TAG, "saveMovie(): movieModel = " + movieModel);
        mDeleteMovieUseCase.init(new DeleteMovieArg(movieModel.getId()))
                .execute(new DeleteMovieSubscriber());
    }

    private final class DeleteMovieSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onNext(Integer integer) {
            Log.v(LOG_TAG, "onNext(): delete row = " + integer);
        }
    }
}
