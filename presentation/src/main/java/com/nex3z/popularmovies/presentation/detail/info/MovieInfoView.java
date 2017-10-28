package com.nex3z.popularmovies.presentation.detail.info;

import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.base.BaseView;

public interface MovieInfoView extends BaseView {

    void renderMovie(MovieModel movie);

}
