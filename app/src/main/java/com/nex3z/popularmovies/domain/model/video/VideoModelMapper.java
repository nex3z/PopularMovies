package com.nex3z.popularmovies.domain.model.video;

import com.nex3z.popularmovies.data.entity.video.VideoEntity;
import com.nex3z.popularmovies.data.entity.video.VideoRespEntity;
import com.nex3z.popularmovies.domain.model.MapperUtil;

import java.util.List;

public class VideoModelMapper {

    private VideoModelMapper() {}

    public static List<VideoModel> transform(VideoRespEntity entity) {
        return entity != null ? transform(entity.getResults()) : null;
    }

    public static VideoModel transform(VideoEntity entity) {
        VideoModel model = null;

        if (entity != null) {
            model = new VideoModel();
            model.setId(entity.getId());
            model.setIso6391(entity.getIso6391());
            model.setIso31661(entity.getIso31661());
            model.setKey(entity.getKey());
            model.setName(entity.getName());
            model.setSite(entity.getSite());
            model.setSize(entity.getSize());
            model.setType(entity.getType());
        }

        return model;
    }

    public static List<VideoModel> transform(List<VideoEntity> entities) {
        return MapperUtil.transform(entities, VideoModelMapper::transform);
    }

}
