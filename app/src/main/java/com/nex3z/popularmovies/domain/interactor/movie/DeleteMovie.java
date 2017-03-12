package com.nex3z.popularmovies.domain.interactor.movie;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import io.reactivex.Observable;


public class DeleteMovie extends UseCase<Integer, DeleteMovie.Params> {

    private final MovieRepository mMovieRepository;

    public DeleteMovie(MovieRepository repository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = repository;
    }

    @Override
    public Observable<Integer> buildUseCaseObservable(Params params) {
        return mMovieRepository.deleteMovie(params.mMovieId);
    }

    public static class Params {
        private final long mMovieId;

        private Params(long movieId) {
            mMovieId = movieId;
        }

        public static Params forMovie(long movieId) {
            return new Params(movieId);
        }
    }
}
