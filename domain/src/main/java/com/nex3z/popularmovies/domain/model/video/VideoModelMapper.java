package com.nex3z.popularmovies.domain.model.video;

import com.nex3z.popularmovies.data.entity.review.GetMovieReviewsResponse;
import com.nex3z.popularmovies.data.entity.video.GetMovieVideosResponse;
import com.nex3z.popularmovies.data.entity.video.VideoEntity;
import com.nex3z.popularmovies.domain.model.MapperUtil;
import com.nex3z.popularmovies.domain.check.Precondition;
import com.nex3z.popularmovies.domain.model.review.ReviewModel;

import java.util.List;

public class VideoModelMapper {

    private VideoModelMapper() {}

    public static List<VideoModel> transform(GetMovieVideosResponse response) {
        Precondition.checkTransformValueNotNull(response);
        return transform(response.getResults());
    }

    public static List<VideoModel> transform(List<VideoEntity> entities) {
        return MapperUtil.transform(entities, VideoModelMapper::transform);
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
