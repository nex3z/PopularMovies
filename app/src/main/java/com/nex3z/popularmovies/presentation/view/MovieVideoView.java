package com.nex3z.popularmovies.presentation.view;

import android.content.Context;

import com.nex3z.popularmovies.domain.model.video.VideoModel;

import java.util.List;

public interface MovieVideoView extends BaseView, LoadDataView {

    void renderVideos(List<VideoModel> videos);

    Context getContext();
}
