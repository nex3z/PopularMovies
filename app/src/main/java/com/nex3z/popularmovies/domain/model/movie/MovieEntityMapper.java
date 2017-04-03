package com.nex3z.popularmovies.domain.model.movie;

import com.nex3z.popularmovies.data.entity.movie.MovieEntity;

public class MovieEntityMapper {

    private MovieEntityMapper() {}

    public static MovieEntity transform(MovieModel model) {
        MovieEntity entity = null;
        
        if (model != null) {
            entity = new MovieEntity();
            entity.setPosterPath(model.getPosterPath());
            entity.setAdult(model.isAdult());
            entity.setOverview(model.getOverview());
            entity.setReleaseDate(model.getReleaseDate());
            entity.setGenreIds(model.getGenreIds());
            entity.setId(model.getId());
            entity.setOriginalTitle(model.getOriginalTitle());
            entity.setOriginalLanguage(model.getOriginalLanguage());
            entity.setTitle(model.getTitle());
            entity.setBackdropPath(model.getBackdropPath());
            entity.setPopularity(model.getPopularity());
            entity.setVoteCount(model.getVoteCount());
            entity.setVideo(model.isVideo());
            entity.setVoteCount(model.getVoteCount());
        }
        
        return entity;
    }
}
