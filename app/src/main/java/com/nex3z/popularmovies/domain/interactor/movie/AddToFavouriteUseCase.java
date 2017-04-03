package com.nex3z.popularmovies.domain.interactor.movie;

import com.nex3z.popularmovies.data.repository.movie.MovieRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieEntityMapper;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;

import io.reactivex.Observable;

public class AddToFavouriteUseCase extends UseCase<Integer, AddToFavouriteUseCase.Params> {

    private final MovieRepository mMovieRepository;

    public AddToFavouriteUseCase(MovieRepository movieRepository,
                                 ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<Integer> buildUseCaseObservable(Params params) {
        return mMovieRepository
                .addToFavourite(MovieEntityMapper.transform(params.mMovieModel));
    }

    public static final class Params {
        private MovieModel mMovieModel;

        private Params(MovieModel movieModel) {
            mMovieModel = movieModel;
        }

        public static Params forMovie(MovieModel movieModel) {
            return new Params(movieModel);
        }
    }
}
