package com.nex3z.popularmovies.data.repository;

import com.nex3z.popularmovies.data.entity.mapper.MovieEntityDataMapper;
import com.nex3z.popularmovies.data.repository.datasource.movie.MovieDataStore;
import com.nex3z.popularmovies.data.repository.datasource.movie.MovieDataStoreFactory;
import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.domain.mapper.MovieDataMapper;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import java.util.List;

import rx.Observable;

public class MovieDataRepository implements MovieRepository {
    private final MovieDataStoreFactory mMovieDataStoreFactory;
    private final MovieEntityDataMapper mMovieEntityDataMapper;
    private final MovieDataMapper mMovieDataMapper;

    public MovieDataRepository(MovieDataStoreFactory factory,
                               MovieEntityDataMapper movieEntityDataMapper,
                               MovieDataMapper movieDataMapper) {
        mMovieDataStoreFactory = factory;
        mMovieEntityDataMapper = movieEntityDataMapper;
        mMovieDataMapper = movieDataMapper;
    }

    @Override
    public Observable<List<Movie>> movies(String sortBy) {
        final MovieDataStore movieDataStore = mMovieDataStoreFactory.createCloudMovieDataStore();
        return movieDataStore
                .getMovieEntityList(sortBy)
                .map(mMovieEntityDataMapper::transform);
    }

    @Override
    public Observable<List<Movie>> movies(String sortBy, int page) {
        final MovieDataStore movieDataStore = mMovieDataStoreFactory.createCloudMovieDataStore();
        return movieDataStore
                .getMovieEntityList(sortBy, page)
                .map(mMovieEntityDataMapper::transform);
    }

    @Override
    public Observable<Long> insertMovie(Movie movie) {
        final MovieDataStore movieDataStore =
                mMovieDataStoreFactory.createContentProviderDataStore();
        return movieDataStore.insertMovieEntity(mMovieDataMapper.toMovieEntity(movie));
    }

    @Override
    public Observable<Integer> deleteMovie(long movieId) {
        final MovieDataStore movieDataStore =
                mMovieDataStoreFactory.createContentProviderDataStore();
        return movieDataStore.deleteMovieEntity(movieId);
    }
}
