package com.nex3z.popularmovies.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.domain.interactor.DefaultObserver;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.movie.CheckFavourite;
import com.nex3z.popularmovies.domain.interactor.movie.CheckFavouriteArg;
import com.nex3z.popularmovies.domain.interactor.movie.DeleteMovie;
import com.nex3z.popularmovies.domain.interactor.movie.DeleteMovieArg;
import com.nex3z.popularmovies.domain.interactor.movie.GetFavouriteMovieList;
import com.nex3z.popularmovies.domain.interactor.movie.GetMovieList;
import com.nex3z.popularmovies.domain.interactor.movie.GetMovieListArg;
import com.nex3z.popularmovies.domain.interactor.movie.SaveMovie;
import com.nex3z.popularmovies.domain.interactor.movie.SaveMovieArg;
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
    private final UseCase mCheckFavouriteUseCase;
    private final MovieModelDataMapper mMovieModelDataMapper;
    private List<MovieModel> mMovies = new ArrayList<>();
    private int mPage = FIRST_PAGE;
    private String mSortBy = GetMovieListArg.SORT_BY_POPULARITY_DESC;

    public MovieListPresenter(UseCase getMovieListUseCase,
                              UseCase saveMovieUseCase,
                              UseCase deleteMovieUseCase,
                              UseCase checkFavouriteUseCase,
                              MovieModelDataMapper movieModelDataMapper) {
        mGetMovieListUseCase = getMovieListUseCase;
        mSaveMovieUseCase = saveMovieUseCase;
        mDeleteMovieUseCase = deleteMovieUseCase;
        mCheckFavouriteUseCase = checkFavouriteUseCase;
        mMovieModelDataMapper = movieModelDataMapper;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        mGetMovieListUseCase.dispose();
        mMovieGridView = null;
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
        // mGetMovieListUseCase.dispose();
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

    private void showMovieCollectionInView(Collection<MovieModel> movieCollection) {
        int currentSize = mMovies.size();
        mMovies.addAll(movieCollection);
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
            mGetMovieListUseCase
                    .execute(new MovieListSubscriber(), GetMovieList.Params.forPage(mSortBy, mPage));
        } else if (mGetMovieListUseCase instanceof GetFavouriteMovieList) {
            Log.v(LOG_TAG, "fetchMovies(): mGetMovieListUseCase instanceof GetFavouriteMovieList");
            mGetMovieListUseCase.execute(new FavouriteMovieListSubscriber(),
                            GetFavouriteMovieList.Params.sortBy(
                                    GetFavouriteMovieList.Params.SORT_BY_ADD_DATE_DESC));
        }

    }

    private final class MovieListSubscriber extends DefaultObserver<List<Movie>> {

        @Override public void onComplete() {
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

            List<MovieModel> movieModels = mMovieModelDataMapper.transform(movies);
            checkFavourite(movieModels);
        }
    }

    private final class FavouriteMovieListSubscriber extends DefaultObserver<List<Movie>> {

        @Override public void onComplete() {
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
            List<MovieModel> movieModels = mMovieModelDataMapper.transform(movies);
            for (MovieModel movieModel : movieModels) {
                movieModel.setFavourite(true);
            }
            showMovieCollectionInView(movieModels);
        }
    }

    @SuppressWarnings("unchecked")
    private void saveMovie(MovieModel movieModel) {
        Log.v(LOG_TAG, "saveMovie(): movieModel = " + movieModel);
        mSaveMovieUseCase.execute(new SaveMovieSubscriber(),
                SaveMovie.Params.forMovie(mMovieModelDataMapper.toMovie(movieModel)));
    }

    private final class SaveMovieSubscriber extends DefaultObserver<Long> {
        @Override
        public void onNext(Long aLong) {
            Log.v(LOG_TAG, "onNext(): saved row id = " + aLong);
        }
    }

    @SuppressWarnings("unchecked")
    private void deleteMovie(MovieModel movieModel) {
        Log.v(LOG_TAG, "deleteMovie(): movieModel = " + movieModel);
        mDeleteMovieUseCase.execute(new DeleteMovieSubscriber(),
                DeleteMovie.Params.forMovie(movieModel.getId()));
    }

    private final class DeleteMovieSubscriber extends DefaultObserver<Integer> {
        @Override
        public void onNext(Integer integer) {
            Log.v(LOG_TAG, "onNext(): delete row = " + integer);
        }
    }

    @SuppressWarnings("unchecked")
    private void checkFavourite(List<MovieModel> movieModels) {
        List<Long> movieIds = new ArrayList<>();
        for (MovieModel movieModel : movieModels) {
            movieIds.add(movieModel.getId());
        }
        Log.v(LOG_TAG, "checkFavourite(): movieIds = " + movieIds);
        mCheckFavouriteUseCase.execute(new CheckFavouriteSubscriber(movieModels),
                CheckFavourite.Params.forMovies(movieIds));
    }

    private final class CheckFavouriteSubscriber extends DefaultObserver<List<Boolean>> {
        private List<MovieModel> mMovieModels;

        public CheckFavouriteSubscriber(List<MovieModel> movieModels) {
            mMovieModels = movieModels;
        }

        @Override
        public void onNext(List<Boolean> results) {
            Log.v(LOG_TAG, "onNext(): results = " + results);
            for (int i = 0; i < mMovieModels.size(); i++) {
                mMovieModels.get(i).setFavourite(results.get(i));
            }
            showMovieCollectionInView(mMovieModels);
        }
    }
}
