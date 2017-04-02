package com.nex3z.popularmovies.presentation.view;

import com.nex3z.popularmovies.domain.model.movie.MovieModel;

public interface MovieInfoView extends BaseView {

    void renderMovie(MovieModel movie);

}
