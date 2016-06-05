package com.nex3z.popularmovies.presentation.ui;

import android.content.Context;

import com.nex3z.popularmovies.presentation.model.VideoModel;

import java.util.Collection;

public interface MovieVideoView extends LoadDataView {

    Context getContext();

    void renderVideos(Collection<VideoModel> videos);

}
