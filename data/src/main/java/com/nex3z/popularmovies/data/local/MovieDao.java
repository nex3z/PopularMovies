package com.nex3z.popularmovies.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nex3z.popularmovies.data.entity.discover.MovieEntity;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    Single<List<MovieEntity>> getMovies();

    @Query("SELECT * FROM movies WHERE id IS :movieId")
    Single<List<MovieEntity>> getMovieById(long movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movie);

    @Delete
    void delete(MovieEntity movie);

}
