package com.nex3z.popularmovies.data.rest.model;

import com.google.gson.annotations.SerializedName;
import com.nex3z.popularmovies.data.model.Movie;
import org.parceler.Parcel;
import java.util.List;

@Parcel
public class MovieResponse {
    long page;

    @SerializedName("results")
    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
