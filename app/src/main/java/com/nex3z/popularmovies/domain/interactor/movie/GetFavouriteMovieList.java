package com.nex3z.popularmovies.domain.interactor.movie;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import rx.Observable;

public class GetFavouriteMovieList extends UseCase<GetFavouriteMovieListArg> {

    private final MovieRepository mMovieRepository;

    public GetFavouriteMovieList(MovieRepository repository, ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = repository;
        mArg = new GetFavouriteMovieListArg();
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mArg == null) {
            throw new IllegalArgumentException("mArg cannot be null.");
        }

        return mMovieRepository.getFavouriteMovies(mArg.getSortBy());
    }
}
