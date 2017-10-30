package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.domain.Context;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.movie.MovieModelMapper;

import java.util.List;

import io.reactivex.Observable;

public class GetFavouriteMoviesUseCase extends BaseUseCase<List<MovieModel>, Void> {

    public GetFavouriteMoviesUseCase(Context context) {
        super(context);
    }

    @Override
    Observable<List<MovieModel>> buildUseCaseObservable(Void aVoid) {
        return mContext.getMovieRepository().getFavouriteMovies()
                .map(MovieModelMapper::transform)
                .toObservable();
    }

}
