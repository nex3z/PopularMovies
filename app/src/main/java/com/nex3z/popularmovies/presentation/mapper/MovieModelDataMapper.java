package com.nex3z.popularmovies.presentation.mapper;

import android.util.Log;

import com.nex3z.popularmovies.domain.Movie;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.util.ImageUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieModelDataMapper {

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
            Log.v("mapper", "transform(): movie.getBackdropPath() = " + movie.getBackdropPath());
            Log.v("mapper", "transform(): url  = " + ImageUtility.getImageUrl(movie.getBackdropPath()));
            movieModel.setBackdropImageUrl(ImageUtility.getImageUrl(movie.getBackdropPath()));
            movieModel.setPosterImageUrl(ImageUtility.getImageUrl(movie.getPosterPath()));
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
}
