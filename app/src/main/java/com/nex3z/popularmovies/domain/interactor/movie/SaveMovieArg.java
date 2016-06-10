package com.nex3z.popularmovies.domain.interactor.movie;

import com.nex3z.popularmovies.domain.Movie;

public class SaveMovieArg {

    private final Movie mMovie;

    public SaveMovieArg(Movie movie) {
        mMovie = movie;
    }

    public Movie getMovie() {
        return mMovie;
    }

}
