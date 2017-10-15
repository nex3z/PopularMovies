package com.nex3z.popularmovies.domain.model.movie;

import com.nex3z.popularmovies.data.entity.discover.DiscoverMovieResponse;
import com.nex3z.popularmovies.data.entity.discover.MovieEntity;
import com.nex3z.popularmovies.domain.model.MapperUtil;
import com.nex3z.popularmovies.domain.check.Precondition;

import java.util.List;

public class MovieModelMapper {

    private MovieModelMapper() {}

    public static List<MovieModel> transform(DiscoverMovieResponse response) {
        Precondition.checkTransformValueNotNull(response);
        return transform(response.getResults());
    }

    public static List<MovieModel> transform(List<MovieEntity> entities) {
        return MapperUtil.transform(entities, MovieModelMapper::transform);
    }

    public static MovieModel transform(MovieEntity entity) {
        Precondition.checkTransformValueNotNull(entity);

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