package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieParams;
import com.nex3z.popularmovies.domain.Context;
import com.nex3z.popularmovies.domain.check.Precondition;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.domain.model.movie.MovieModelMapper;

import java.util.List;

import io.reactivex.Observable;

public class DiscoverMoviesUseCase extends BaseUseCase<List<MovieModel>, DiscoverMovieParams>  {

    public DiscoverMoviesUseCase(Context context) {
        super(context);
    }

    @Override
    Observable<List<MovieModel>> buildUseCaseObservable(DiscoverMovieParams params) {
        return mContext.getMovieRepository()
                .discoverMovies(params)
                .map(MovieModelMapper::transform)
                .toObservable();
    }

}
