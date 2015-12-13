package com.nex3z.popularmovies.ui.adapter;

import com.nex3z.popularmovies.data.model.Movie;

import java.util.List;


public class MovieAdapter extends AbstractMovieAdapter{

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private List<Movie> mMovies;

    public MovieAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public Movie getMovie(int position) {
        return mMovies.get(position);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
