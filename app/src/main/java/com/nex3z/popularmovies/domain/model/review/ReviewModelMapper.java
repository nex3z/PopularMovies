package com.nex3z.popularmovies.domain.model.review;

import com.nex3z.popularmovies.data.entity.review.ReviewEntity;
import com.nex3z.popularmovies.data.entity.review.ReviewRespEntity;
import com.nex3z.popularmovies.domain.model.MapperUtil;

import java.util.List;

public class ReviewModelMapper {

    private ReviewModelMapper() {}

    public static List<ReviewModel> transform(ReviewRespEntity entity) {
        return entity != null ? transform(entity.getResults()) : null;
    }

    public static ReviewModel transform(ReviewEntity entity) {
        ReviewModel model = null;

        if (entity != null) {
            model = new ReviewModel();
            model.setId(entity.getId());
            model.setAuthor(entity.getAuthor());
            model.setContent(entity.getContent());
            model.setUrl(entity.getUrl());
        }

        return model;
    }

    public static List<ReviewModel> transform(List<ReviewEntity> entities) {
        return MapperUtil.transform(entities, ReviewModelMapper::transform);
    }

}
