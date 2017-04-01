package com.nex3z.popularmovies.domain.interactor.movie;

import com.nex3z.popularmovies.data.repository.movie.MovieRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.movie.MovieModelMapper;

import java.util.List;

import io.reactivex.Observable;

public class DiscoverMovieUseCase extends UseCase<List<MovieModel>, DiscoverMovieUseCase.Params> {

    private final MovieRepository mMovieRepository;

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
                .map(MovieModelMapper::transform);
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
