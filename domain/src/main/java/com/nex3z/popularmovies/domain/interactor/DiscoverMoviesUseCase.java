package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieParams;
import com.nex3z.popularmovies.domain.Context;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.movie.MovieModelMapper;

import java.util.List;

import io.reactivex.Observable;

public class DiscoverMoviesUseCase extends BaseUseCase<List<MovieModel>, DiscoverMovieParams>  {
    private static final String LOG_TAG = DiscoverMoviesUseCase.class.getSimpleName();

    public DiscoverMoviesUseCase(Context context) {
        super(context);
    }

    @Override
    Observable<List<MovieModel>> buildUseCaseObservable(DiscoverMovieParams params) {
        return mContext.getMovieRepository()
                .discoverMovies(params)
                .map(MovieModelMapper::transform)
                .toObservable()
                .flatMap(Observable::fromIterable)
                .flatMap(this::checkFavourite, this::setFavourite)
                .toList()
                .toObservable();
    }

    private Observable<Boolean> checkFavourite(MovieModel movie) {
        return mContext.getMovieRepository().isFavouriteMovie(movie.getId()).toObservable();
    }

    private MovieModel setFavourite(MovieModel movie, boolean isFavourite) {
        movie.setFavourite(isFavourite);
        return movie;
    }

}
