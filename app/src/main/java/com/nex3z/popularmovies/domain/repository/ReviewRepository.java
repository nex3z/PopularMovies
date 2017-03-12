package com.nex3z.popularmovies.domain.repository;

import com.nex3z.popularmovies.domain.Review;

import java.util.List;

import io.reactivex.Observable;

public interface ReviewRepository {
    Observable<List<Review>> reviews(long movieId);
}
