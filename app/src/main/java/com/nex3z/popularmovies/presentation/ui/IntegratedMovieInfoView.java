package com.nex3z.popularmovies.presentation.ui;

import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.model.ReviewModel;
import com.nex3z.popularmovies.presentation.model.VideoModel;

import java.util.Collection;

public interface IntegratedMovieInfoView extends LoadDataView {

    void renderMovie(MovieModel movie);

    void renderVideos(Collection<VideoModel> videos);

    void renderReviews(Collection<ReviewModel> reviews);
}
