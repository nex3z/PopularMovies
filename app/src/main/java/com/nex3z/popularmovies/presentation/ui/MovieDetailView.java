package com.nex3z.popularmovies.presentation.ui;

import com.nex3z.popularmovies.presentation.model.MovieModel;

public interface MovieDetailView extends LoadDataView {

    void renderMovie(MovieModel movie);

}
