package com.nex3z.popularmovies.domain.interactor;

public class MovieInfoArg {
    public static final long MOVIE_ID_NOT_INITIALIZED = -1;

    private final long mMovieId;

    public MovieInfoArg(long movieId) {
        mMovieId = movieId;
    }

    public MovieInfoArg() {
        this(MOVIE_ID_NOT_INITIALIZED);
    }

    public long getMovieId() {
        return mMovieId;
    }
}
