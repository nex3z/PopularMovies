package com.nex3z.popularmovies.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.nex3z.popularmovies.data.entity.discover.MovieEntity;

@Database(entities = {MovieEntity.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

}
