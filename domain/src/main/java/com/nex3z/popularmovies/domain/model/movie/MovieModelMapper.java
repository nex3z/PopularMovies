package com.nex3z.popularmovies.domain.model.movie;

import com.nex3z.popularmovies.data.entity.discover.MovieEntity;
import com.nex3z.popularmovies.domain.model.MapperUtil;

import java.util.List;

public class MovieModelMapper {

    private MovieModelMapper() {}

    public static List<MovieModel> transform(List<MovieEntity> entities) {
        return MapperUtil.transform(entities, MovieModelMapper::transform);
    }

    public static MovieModel transform(MovieEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Cannot transform null value");
        }

        final MovieModel model = new MovieModel(entity.getId());

        model.setVoteCount(entity.getVoteCount());
        model.setVideo(entity.isVideo());
        model.setVoteAverage(entity.getVoteAverage());
        model.setTitle(entity.getTitle());
        model.setPopularity(entity.getPopularity());
        model.setPosterPath(entity.getPosterPath());
        model.setOriginalLanguage(entity.getOriginalLanguage());
        model.setOriginalTitle(entity.getOriginalTitle());
        model.setGenreIds(entity.getGenreIds());
        model.setBackdropPath(entity.getBackdropPath());
        model.setAdult(entity.isAdult());
        model.setOverview(entity.getOverview());
        model.setReleaseDate(entity.getReleaseDate());

        return model;
    }

}
