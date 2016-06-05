package com.nex3z.popularmovies.domain.interactor;


import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import rx.Observable;

public class GetMovieList extends UseCase<GetMovieListArg> {

    private final MovieRepository mMovieRepository;

    public GetMovieList(MovieRepository repository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = repository;
        mArg = new GetMovieListArg();
    }

    @Override
    protected Observable buildUseCaseObservable() {
        if (mArg.getPage() < 0) {
            return mMovieRepository.movies(mArg.getSortBy());
        } else {
            return mMovieRepository.movies(mArg.getSortBy(), mArg.getPage());
        }
    }
}
