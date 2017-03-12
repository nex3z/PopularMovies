package com.nex3z.popularmovies.domain.interactor.movie;


import android.util.Log;

import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import java.util.List;

import io.reactivex.Observable;

public class GetMovieList extends UseCase<List<Movie>, GetMovieList.Params> {

    private final MovieRepository mMovieRepository;

    public GetMovieList(MovieRepository repository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = repository;
    }

    @Override
    public Observable<List<Movie>> buildUseCaseObservable(Params params) {
        return mMovieRepository.movies(params.mSortBy, params.mPage);
    }

    public static class Params {
        public static final int INVALID_PAGE = -1;

        public static final String SORT_BY_POPULARITY_DESC = "popularity.desc";
        public static final String SORT_BY_VOTE_AVERAGE_DESC = "vote_average.desc";
        public static final String SORT_BY_VOTE_COUNT_DESC = "vote_count.desc";
        public static final String SORT_BY_RELEASE_DATE_DESC = "release_date.desc";

        private final String mSortBy;
        private final int mPage;

        private Params(String sortBy, int page) {
            mSortBy = sortBy;
            mPage = page;
        }

        public static Params forPage(String sortBy, int page) {
            return new Params(sortBy, page);
        }
    }
}
