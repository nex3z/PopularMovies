package com.nex3z.popularmovies.domain.interactor.movie;

import android.util.Log;

import com.nex3z.popularmovies.data.repository.movie.MovieRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.movie.MovieModelMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DiscoverMovieUseCase extends UseCase<List<MovieModel>, DiscoverMovieUseCase.Params> {
    private static final String LOG_TAG = DiscoverMovieUseCase.class.getSimpleName();

    private final MovieRepository mMovieRepository;
    private List<MovieModel> mMovieModels;

    public DiscoverMovieUseCase(MovieRepository movieRepository,
                                ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<List<MovieModel>> buildUseCaseObservable(Params params) {
        return mMovieRepository
                .discoverMovies(params.mPage, params.mSortBy)
                .map(MovieModelMapper::transform)
                .doOnNext(movieModels ->  mMovieModels = movieModels)
                .flatMap(movieModels -> mMovieRepository.isFavourite(getMovieIds(movieModels)))
                .map(this::update);
    }

    private List<Long> getMovieIds(List<MovieModel> movieModels) {
        List<Long> ids = new ArrayList<>(movieModels.size());
        for (MovieModel movieModel : movieModels) {
            ids.add(movieModel.getId());
        }
        Log.v(LOG_TAG, "getMovieIds(): ids = " + ids);
        return ids;
    }

    private List<MovieModel> update(List<Boolean> favourites) {
        for (int i = 0; i < mMovieModels.size(); i++) {
            mMovieModels.get(i).setFavourite(favourites.get(i));
        }
        return mMovieModels;
    }

    public static final class Params {
        public static final String SORT_BY_POPULARITY_DESC = "popularity.desc";
        public static final String SORT_BY_VOTE_AVERAGE_DESC = "vote_average.desc";
        public static final String SORT_BY_VOTE_COUNT_DESC = "vote_count.desc";
        public static final String SORT_BY_RELEASE_DATE_DESC = "release_date.desc";

        private String mSortBy;
        private int mPage;

        private Params(int page, String sortBy) {
            mSortBy = sortBy;
            mPage = page;
        }

        public static Params forPage(int page, String sortBy) {
            return new Params(page, sortBy);
        }
    }
}
