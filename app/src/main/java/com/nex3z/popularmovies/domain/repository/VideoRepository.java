package com.nex3z.popularmovies.domain.repository;

import com.nex3z.popularmovies.domain.Video;

import java.util.List;

import io.reactivex.Observable;

public interface VideoRepository {
    Observable<List<Video>> videos(long movieId);
}
