package com.nex3z.popularmovies.domain.interactor.movie;

import com.nex3z.popularmovies.data.repository.movie.MovieRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.movie.MovieModelMapper;

import java.util.List;

import io.reactivex.Observable;

public class GetFavouriteMovieUseCase extends UseCase<List<MovieModel>, Void> {

    private final MovieRepository mMovieRepository;

    public GetFavouriteMovieUseCase(MovieRepository movieRepository,
                                    ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<List<MovieModel>> buildUseCaseObservable(Void aVoid) {
        return mMovieRepository
                .getFavouriteMovies()
                .map(MovieModelMapper::transform);
    }
}
