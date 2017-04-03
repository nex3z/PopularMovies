package com.nex3z.popularmovies.domain.interactor.movie;

import com.nex3z.popularmovies.data.repository.movie.MovieRepository;
import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.model.movie.MovieEntityMapper;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;

import io.reactivex.Observable;

public class SetFavouriteUseCase extends UseCase<Boolean, SetFavouriteUseCase.Params> {

    private final MovieRepository mMovieRepository;

    public SetFavouriteUseCase(MovieRepository movieRepository,
                               ThreadExecutor threadExecutor,
                               PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMovieRepository = movieRepository;
    }

    @Override
    public Observable<Boolean> buildUseCaseObservable(Params params) {
        if (params.mAddToFavourite) {
            return mMovieRepository
                    .addToFavourite(MovieEntityMapper.transform(params.mMovieModel))
                    .map(integer -> true);
        } else  {
            return mMovieRepository
                    .removeFromFavourite(params.mMovieModel.getId())
                    .map(integer -> false);
        }
    }

    public static final class Params {
        private MovieModel mMovieModel;
        private boolean mAddToFavourite;

        private Params(MovieModel movieModel, boolean addToFavourite) {
            mMovieModel = movieModel;
            mAddToFavourite = addToFavourite;
        }

        public static Params forMovie(MovieModel movieModel, boolean addToFavourite) {
            return new Params(movieModel, addToFavourite);
        }
    }
}
