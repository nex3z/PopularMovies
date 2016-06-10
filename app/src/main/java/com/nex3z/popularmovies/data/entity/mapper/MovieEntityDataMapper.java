package com.nex3z.popularmovies.data.entity.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.nex3z.popularmovies.data.entity.MovieEntity;
import com.nex3z.popularmovies.data.provider.MovieContract;
import com.nex3z.popularmovies.domain.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class MovieEntityDataMapper {

    public Movie transform(MovieEntity movieEntity) {
        Movie movie = null;
        if (movieEntity != null) {
            movie = new Movie();
            movie.setAdult(movieEntity.isAdult());
            movie.setBackdropPath(movieEntity.getBackdropPath());
            movie.setGenreIds(movieEntity.getGenreIds());
            movie.setId(movieEntity.getId());
            movie.setOriginalLanguage(movieEntity.getOriginalLanguage());
            movie.setOriginalTitle(movieEntity.getOriginalTitle());
            movie.setOverview(movieEntity.getOverview());
            movie.setPopularity(movieEntity.getPopularity());
            movie.setPosterPath(movieEntity.getPosterPath());
            movie.setReleaseDate(movieEntity.getReleaseDate());
            movie.setTitle(movieEntity.getTitle());
            movie.setVideo(movieEntity.isVideo());
            movie.setVoteAverage(movieEntity.getVoteAverage());
            movie.setVoteCount(movieEntity.getVoteCount());
        }

        return movie;
    }

    public List<Movie> transform(Collection<MovieEntity> movieEntityCollection) {
        List<Movie> movieList = new ArrayList<>();

        if (movieEntityCollection != null) {
            Movie movie;
            for (MovieEntity movieEntity : movieEntityCollection) {
                movie = transform(movieEntity);
                if (movie != null) {
                    movieList.add(movie);
                }
            }
        }

        return movieList;
    }

    public ContentValues toContentValues(MovieEntity movieEntity) {
        ContentValues movieValues = new ContentValues();

        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieEntity.getId());
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movieEntity.getTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movieEntity.getVoteCount());
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movieEntity.getVoteAverage());
        movieValues.put(MovieContract.MovieEntry.COLUMN_ADULT, movieEntity.isAdult());
        movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movieEntity.getBackdropPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_GENRE_IDS, movieEntity.getGenreIds().toString());
        movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, movieEntity.getOriginalLanguage());
        movieValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movieEntity.getOriginalTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieEntity.getOverview());
        movieValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, movieEntity.getPopularity());
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movieEntity.getPosterPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movieEntity.getReleaseDate());
        movieValues.put(MovieContract.MovieEntry.COLUMN_VIDEO, movieEntity.isVideo());

        return movieValues;
    }

}
