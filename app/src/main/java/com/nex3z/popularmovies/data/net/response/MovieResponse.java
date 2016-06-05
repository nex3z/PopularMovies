package com.nex3z.popularmovies.data.net.response;

import com.google.gson.annotations.SerializedName;
import com.nex3z.popularmovies.data.entity.MovieEntity;

import java.util.List;

public class MovieResponse {
    private long page;

    @SerializedName("results")
    private List<MovieEntity> movies;

    public List<MovieEntity> getMovies() {
        return movies;
    }
}
