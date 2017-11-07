package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.data.entity.discover.MovieEntity;
import com.nex3z.popularmovies.domain.Context;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.movie.MovieModelMapper;

import io.reactivex.Observable;

public class SetMovieFavouriteUseCase
        extends BaseUseCase<Boolean, SetMovieFavouriteUseCase.Params> {

    public SetMovieFavouriteUseCase(Context context) {
        super(context);
    }

    @Override
    Observable<Boolean> buildUseCaseObservable(Params params) {
        return Observable.create(emitter -> {
            if (params.mFavourite) {
                mContext.getMovieRepository().addMovieToFavourite(params.mMovie);
            } else {
                mContext.getMovieRepository().removeMovieFromFavourite(params.mMovie);
            }
            emitter.onNext(params.mFavourite);
            emitter.onComplete();
        });
    }

    public static class Params {
        private final MovieEntity mMovie;
        private final boolean mFavourite;

        private Params(MovieModel movie, boolean favourite) {
            mMovie = MovieModelMapper.toEntity(movie);
            mFavourite = favourite;
        }

        public static Params forMovie(MovieModel movie, boolean favourite) {
            return new Params(movie, favourite);
        }
    }
}
