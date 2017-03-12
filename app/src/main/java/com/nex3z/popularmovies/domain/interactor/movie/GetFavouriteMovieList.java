package com.nex3z.popularmovies.domain.interactor.movie;

import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import java.util.List;

import io.reactivex.Observable;


public class GetFavouriteMovieList extends UseCase<List<Movie>, GetFavouriteMovieList.Params> {

    private final MovieRepository mMovieRepository;

    public GetFavouriteMovieList(MovieRepository repository, ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = repository;
    }

    @Override
    public Observable<List<Movie>> buildUseCaseObservable(Params params) {
        return mMovieRepository.getFavouriteMovies(params.mSortBy);
    }

    public static class Params {
        public static final String SORT_BY_ADD_DATE_DESC = "add_date_desc";

        private final String mSortBy;

        private Params(String sortBy) {
            mSortBy = sortBy;
        }

        public static Params sortBy(String sortBy) {
            return new Params(sortBy);
        }
    }
}
