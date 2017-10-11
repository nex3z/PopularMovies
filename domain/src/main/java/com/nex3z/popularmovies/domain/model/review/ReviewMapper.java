package com.nex3z.popularmovies.domain.model.review;

import com.nex3z.popularmovies.data.entity.review.ReviewEntity;
import com.nex3z.popularmovies.domain.model.MapperUtil;

import java.util.List;

public class ReviewMapper {

    private ReviewMapper() {}

    public static List<ReviewModel> transform(List<ReviewEntity> entities) {
        return MapperUtil.transform(entities, ReviewMapper::transform);
    }

    public static ReviewModel transform(ReviewEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Cannot transform null value");
        }

        ReviewModel model = new ReviewModel(entity.getId());

        model.setAuthor(entity.getAuthor());
        model.setContent(entity.getContent());
        model.setUrl(entity.getUrl());

        return model;
    }

}
