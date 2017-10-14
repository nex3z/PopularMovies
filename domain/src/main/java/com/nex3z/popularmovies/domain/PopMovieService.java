package com.nex3z.popularmovies.domain;

import com.nex3z.popularmovies.data.repository.MovieRepository;
import com.nex3z.popularmovies.domain.exception.CreateUseCaseFailedException;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.BaseUseCase;

import java.lang.reflect.InvocationTargetException;

public class PopMovieService extends Context implements UseCaseFactory {
    private static final String LOG_TAG = PopMovieService.class.getSimpleName();

    public PopMovieService(MovieRepository movieRepository, ThreadExecutor threadExecutor,
                           PostExecutionThread postExecutionThread) {
        super(movieRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public <T extends BaseUseCase> T create(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor(Context.class).newInstance(this);
        } catch (NoSuchMethodException e) {
            throw new CreateUseCaseFailedException(
                    clazz.getName() + " has no constructor with Context", e);
        } catch (InstantiationException e) {
            throw new CreateUseCaseFailedException(
                    "Failed to instantiation " + clazz.getName(), e);
        } catch (InvocationTargetException e) {
            throw new CreateUseCaseFailedException(
                    "Failed to invoke constructor of " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw new CreateUseCaseFailedException(
                    "Failed to access " + clazz.getName(), e);
        }
    }

}
