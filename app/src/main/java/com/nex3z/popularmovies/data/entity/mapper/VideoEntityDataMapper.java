package com.nex3z.popularmovies.data.entity.mapper;

import com.nex3z.popularmovies.data.entity.VideoEntity;
import com.nex3z.popularmovies.domain.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoEntityDataMapper {

    public Video transform(VideoEntity videoEntity) {
        Video video = null;
        if (videoEntity != null) {
            video = new Video();
            video.setId(videoEntity.getId());
            video.setIso(videoEntity.getIso());
            video.setKey(videoEntity.getKey());
            video.setName(videoEntity.getName());
            video.setSite(videoEntity.getSite());
            video.setSize(videoEntity.getSize());
            video.setType(videoEntity.getType());
        }

        return video;
    }

    public List<Video> transform(List<VideoEntity> videoEntityCollection) {
        List<Video> videoList = new ArrayList<>();
        Video video;
        for (VideoEntity videoEntity : videoEntityCollection)  {
            video = transform(videoEntity);
            if (video != null) {
                videoList.add(video);
            }
        }

        return videoList;
    }

}
