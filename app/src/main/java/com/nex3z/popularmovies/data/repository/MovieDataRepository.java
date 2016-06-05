package com.nex3z.popularmovies.data.repository;

import com.nex3z.popularmovies.data.entity.mapper.MovieEntityDataMapper;
import com.nex3z.popularmovies.data.repository.datasource.MovieDataStore;
import com.nex3z.popularmovies.data.repository.datasource.MovieDataStoreFactory;
import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.domain.repository.MovieRepository;

import java.util.List;

import rx.Observable;

public class MovieDataRepository implements MovieRepository {
    private final MovieDataStoreFactory mMovieDataStoreFactory;
    private final MovieEntityDataMapper mMovieEntityDataMapper;

    public MovieDataRepository(MovieDataStoreFactory factory, MovieEntityDataMapper mapper) {
        mMovieDataStoreFactory = factory;
        mMovieEntityDataMapper = mapper;
    }

    @Override
    public Observable<List<Movie>> movies(String sortBy) {
        final MovieDataStore movieDataStore = mMovieDataStoreFactory.createCloudMovieDataStore();
        return movieDataStore.movieEntityList(sortBy).map(mMovieEntityDataMapper::transform);
    }

    @Override
    public Observable<List<Movie>> movies(String sortBy, int page) {
        final MovieDataStore movieDataStore = mMovieDataStoreFactory.createCloudMovieDataStore();
        return movieDataStore.movieEntityList(sortBy, page).map(mMovieEntityDataMapper::transform);
    }

}
