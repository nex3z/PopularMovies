package com.nex3z.popularmovies.presentation.mapper;

import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.util.ImageUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class MovieModelDataMapper {

    @Inject
    public MovieModelDataMapper() {}

    public MovieModel transform(Movie movie) {
        MovieModel movieModel = null;
        if (movie != null) {
            movieModel = new MovieModel();
            movieModel.setAdult(movie.isAdult());
            movieModel.setBackdropPath(movie.getBackdropPath());
            movieModel.setGenreIds(movie.getGenreIds());
            movieModel.setId(movie.getId());
            movieModel.setOriginalLanguage(movie.getOriginalLanguage());
            movieModel.setOriginalTitle(movie.getOriginalTitle());
            movieModel.setOverview(movie.getOverview());
            movieModel.setPopularity(movie.getPopularity());
            movieModel.setPosterPath(movie.getPosterPath());
            movieModel.setReleaseDate(movie.getReleaseDate());
            movieModel.setTitle(movie.getTitle());
            movieModel.setVideo(movie.isVideo());
            movieModel.setVoteAverage(movie.getVoteAverage());
            movieModel.setVoteCount(movie.getVoteCount());
            movieModel.setBackdropImageUrl(ImageUtility.getBackdropImageUrl(movie.getBackdropPath()));
            movieModel.setPosterImageUrl(ImageUtility.getPosterImageUrl(movie.getPosterPath()));
        }

        return movieModel;
    }

    public List<MovieModel> transform(Collection<Movie> movieCollection) {
        List<MovieModel> movieModelList = new ArrayList<>();
        MovieModel movieModel;
        for (Movie movie : movieCollection) {
            movieModel = transform(movie);
            if (movieModel != null) {
                movieModelList.add(movieModel);
            }
        }

        return movieModelList;
    }

    public Movie toMovie(MovieModel movieModel) {
        Movie movie = null;
        if (movieModel != null) {
            movie = new Movie();
            movie.setAdult(movieModel.isAdult());
            movie.setBackdropPath(movieModel.getBackdropPath());
            movie.setGenreIds(movieModel.getGenreIds());
            movie.setId(movieModel.getId());
            movie.setOriginalLanguage(movieModel.getOriginalLanguage());
            movie.setOriginalTitle(movieModel.getOriginalTitle());
            movie.setOverview(movieModel.getOverview());
            movie.setPopularity(movieModel.getPopularity());
            movie.setPosterPath(movieModel.getPosterPath());
            movie.setReleaseDate(movieModel.getReleaseDate());
            movie.setTitle(movieModel.getTitle());
            movie.setVideo(movieModel.isVideo());
            movie.setVoteAverage(movieModel.getVoteAverage());
            movie.setVoteCount(movieModel.getVoteCount());
        }

        return movie;
    }
}
