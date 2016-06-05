package com.nex3z.popularmovies.presentation.mapper;

import com.nex3z.popularmovies.domain.Video;
import com.nex3z.popularmovies.presentation.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class VideoModelDataMapper {

    public VideoModel transform(Video video) {
        VideoModel videoModel = null;
        if (video != null) {
            videoModel = new VideoModel();
            videoModel.setId(video.getId());
            videoModel.setIso(video.getIso());
            videoModel.setKey(video.getKey());
            videoModel.setName(video.getName());
            videoModel.setSite(video.getSite());
            videoModel.setSize(video.getSize());
            videoModel.setType(video.getType());
        }

        return videoModel;
    }

    public List<VideoModel> transform(List<Video> videoCollection) {
        List<VideoModel> videoModelList = new ArrayList<>();
        VideoModel videoModel;
        for (Video video : videoCollection)  {
            videoModel = transform(video);
            if (videoModel != null) {
                videoModelList.add(videoModel);
            }
        }

        return videoModelList;
    }

}
