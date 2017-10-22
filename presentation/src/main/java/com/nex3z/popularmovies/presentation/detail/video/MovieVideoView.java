package com.nex3z.popularmovies.presentation.detail.video;

import com.nex3z.popularmovies.domain.model.video.VideoModel;
import com.nex3z.popularmovies.presentation.base.BaseView;
import com.nex3z.popularmovies.presentation.base.LoadDataView;

import java.util.List;

public interface MovieVideoView extends BaseView, LoadDataView {

    void renderVideos(List<VideoModel> videos);

    void playVideo(VideoModel video);

}
