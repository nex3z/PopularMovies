package com.nex3z.popularmovies.domain.interactor.movie;


import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import io.reactivex.Observable;

public class SaveMovie extends UseCase<Long, SaveMovie.Params> {

    private final MovieRepository mMovieRepository;

    public SaveMovie(MovieRepository repository, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = repository;
    }

    @Override
    public Observable<Long> buildUseCaseObservable(Params params) {
        return mMovieRepository.insertMovie(params.mMovie);
    }

    public static class Params {
        private final Movie mMovie;

        private Params(Movie movie) {
            mMovie = movie;
        }

        public static Params forMovie(Movie movie) {
            return new Params(movie);
        }
    }

}
