package com.nex3z.popularmovies.domain;

import com.nex3z.popularmovies.data.repository.MovieRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;

public abstract class Context {

    private MovieRepository mMovieRepository;
    private PostExecutionThread mPostExecutionThread;
    private ThreadExecutor mThreadExecutor;

    public Context(MovieRepository movieRepository, PostExecutionThread postExecutionThread,
                   ThreadExecutor threadExecutor) {
        mMovieRepository = movieRepository;
        mPostExecutionThread = postExecutionThread;
        mThreadExecutor = threadExecutor;
    }

    public MovieRepository getMovieRepository() {
        return mMovieRepository;
    }

    public PostExecutionThread getPostExecutionThread() {
        return mPostExecutionThread;
    }

    public ThreadExecutor getThreadExecutor() {
        return mThreadExecutor;
    }

}
