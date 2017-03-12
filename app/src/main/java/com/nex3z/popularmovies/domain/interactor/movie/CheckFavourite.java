package com.nex3z.popularmovies.domain.interactor.movie;


import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import java.util.List;

import io.reactivex.Observable;


public class CheckFavourite extends UseCase<List<Boolean>, CheckFavourite.Params> {

    private final MovieRepository mMovieRepository;

    public CheckFavourite(MovieRepository repository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = repository;
    }

    @Override
    public Observable<List<Boolean>> buildUseCaseObservable(Params params) {
        return mMovieRepository.checkFavourite(params.mMovieIds);
    }

    public static class Params {
        private final List<Long> mMovieIds;

        private Params(List<Long> movieIds) {
            mMovieIds = movieIds;
        }

        public static Params forMovies(List<Long> movieIds) {
            return new Params(movieIds);
        }

    }
}
