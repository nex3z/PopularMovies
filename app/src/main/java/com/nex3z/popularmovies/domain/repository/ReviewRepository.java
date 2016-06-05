package com.nex3z.popularmovies.domain.repository;

import com.nex3z.popularmovies.domain.Review;

import java.util.List;

import rx.Observable;

public interface ReviewRepository {
    Observable<List<Review>> reviews(long movieId);
}
