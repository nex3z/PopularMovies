package com.nex3z.popularmovies.domain.model.movie;

import com.nex3z.popularmovies.data.entity.movie.DiscoveryMovieRespEntity;
import com.nex3z.popularmovies.data.entity.movie.MovieEntity;
import com.nex3z.popularmovies.domain.model.MapperUtil;

import java.util.List;

public class MovieModelMapper {

    public static List<MovieModel> transform(DiscoveryMovieRespEntity entity) {
        return entity != null ? transform(entity.getResults()) : null;
    }

    public static MovieModel transform(MovieEntity entity) {
        MovieModel movieModel = null;

        if (entity != null) {
            movieModel = new MovieModel();
            movieModel.setPosterPath(entity.getPosterPath());
            movieModel.setAdult(entity.isAdult());
            movieModel.setOverview(entity.getOverview());
            movieModel.setReleaseDate(entity.getReleaseDate());
            movieModel.setGenreIds(entity.getGenreIds());
            movieModel.setId(entity.getId());
            movieModel.setOriginalTitle(entity.getOriginalTitle());
            movieModel.setOriginalLanguage(entity.getOriginalLanguage());
            movieModel.setTitle(entity.getTitle());
            movieModel.setBackdropPath(entity.getBackdropPath());
            movieModel.setPopularity(entity.getPopularity());
            movieModel.setVoteCount(entity.getVoteCount());
            movieModel.setVideo(entity.isVideo());
            movieModel.setVoteCount(entity.getVoteCount());
        }

        return movieModel;
    }

    public static List<MovieModel> transform(List<MovieEntity> entities) {
        return MapperUtil.transform(entities, MovieModelMapper::transform);
    }

}
