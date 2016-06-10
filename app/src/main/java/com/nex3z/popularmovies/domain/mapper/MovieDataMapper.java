package com.nex3z.popularmovies.domain.mapper;


import com.nex3z.popularmovies.data.entity.MovieEntity;
import com.nex3z.popularmovies.domain.Movie;

public class MovieDataMapper {

    public MovieEntity toMovieEntity(Movie movie) {
        MovieEntity movieEntity = null;
        if (movie != null) {
            movieEntity = new MovieEntity();
            movieEntity.setAdult(movie.isAdult());
            movieEntity.setBackdropPath(movie.getBackdropPath());
            movieEntity.setGenreIds(movie.getGenreIds());
            movieEntity.setId(movie.getId());
            movieEntity.setOriginalLanguage(movie.getOriginalLanguage());
            movieEntity.setOriginalTitle(movie.getOriginalTitle());
            movieEntity.setOverview(movie.getOverview());
            movieEntity.setPopularity(movie.getPopularity());
            movieEntity.setPosterPath(movie.getPosterPath());
            movieEntity.setReleaseDate(movie.getReleaseDate());
            movieEntity.setTitle(movie.getTitle());
            movieEntity.setVideo(movie.isVideo());
            movieEntity.setVoteAverage(movie.getVoteAverage());
            movieEntity.setVoteCount(movie.getVoteCount());
        }

        return movieEntity;

    }
}
