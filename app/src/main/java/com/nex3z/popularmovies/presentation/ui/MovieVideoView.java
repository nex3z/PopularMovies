package com.nex3z.popularmovies.presentation.ui;

import com.nex3z.popularmovies.presentation.model.VideoModel;

import java.util.Collection;

public interface MovieVideoView extends LoadDataView {

    void renderVideos(Collection<VideoModel> videos);

}
