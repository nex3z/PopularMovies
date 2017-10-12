package com.nex3z.popularmovies.domain.model.video;

import com.nex3z.popularmovies.data.entity.video.VideoEntity;
import com.nex3z.popularmovies.domain.model.MapperUtil;
import com.nex3z.popularmovies.domain.check.Precondition;

import java.util.List;

public class VideoMapper {

    private VideoMapper() {}

    public static List<VideoModel> transform(List<VideoEntity> entities) {
        return MapperUtil.transform(entities, VideoMapper::transform);
    }

    public static VideoModel transform(VideoEntity entity) {
        Precondition.checkTransformValueNotNull(entity);

        VideoModel model = new VideoModel(entity.getId());

        model.setIso6391(entity.getIso6391());
        model.setIso31661(entity.getIso31661());
        model.setKey(entity.getKey());
        model.setName(entity.getName());
        model.setSite(entity.getSite());
        model.setSize(entity.getSize());
        model.setType(entity.getType());

        return model;
    }

}
