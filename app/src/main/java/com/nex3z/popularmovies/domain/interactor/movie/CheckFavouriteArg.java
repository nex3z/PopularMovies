package com.nex3z.popularmovies.domain.interactor.movie;


import java.util.List;

public class CheckFavouriteArg {

    private final List<Long> mMovieIds;

    public CheckFavouriteArg( List<Long> movieIds) {
        mMovieIds = movieIds;
    }

    public List<Long> getMovieIds() {
        return mMovieIds;
    }
}
