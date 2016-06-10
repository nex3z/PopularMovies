package com.nex3z.popularmovies.data.repository.datasource.movie;

import android.net.Uri;
import android.util.Log;

import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.entity.MovieEntity;
import com.nex3z.popularmovies.data.entity.mapper.MovieCursorDataMapper;
import com.nex3z.popularmovies.data.entity.mapper.MovieEntityDataMapper;
import com.nex3z.popularmovies.data.provider.MovieContract;
import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ContentProviderDataStore implements MovieDataStore {
    private static final String LOG_TAG = ContentProviderDataStore.class.getSimpleName();

    private static final String sMovieIdSelection = MovieContract.MovieEntry.TABLE_NAME + "." +
            MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?";

    private final BriteContentResolver mBriteContentResolver;
    private final MovieCursorDataMapper mMovieCursorDataMapper;
    private final MovieEntityDataMapper mMovieEntityDataMapper;


    public ContentProviderDataStore() {
        SqlBrite sqlBrite = SqlBrite.create();
        mBriteContentResolver = sqlBrite.wrapContentProvider(
                App.getAppContext().getContentResolver(), Schedulers.io());
        mMovieCursorDataMapper = new MovieCursorDataMapper();
        mMovieEntityDataMapper = new MovieEntityDataMapper();
    }

    @Override
    public Observable<List<MovieEntity>> getMovieEntityList(String sortBy) {
        return mBriteContentResolver
                .createQuery(
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null,
                        true)
                .map(SqlBrite.Query::run)
                .map(mMovieCursorDataMapper::transformList);
    }

    @Override
    public Observable<List<MovieEntity>> getMovieEntityList(String sortBy, int page) {
        if (page == 1) {
            return getMovieEntityList(sortBy);
        } else {
            throw new UnsupportedOperationException(
                    "The getMovieEntityList method with page greater than 1 is not supported in ContentProvider store");
        }
    }

    @Override
    public Observable<Long> insertMovieEntity(MovieEntity movie) {
        return Observable.<Long>create(subscriber -> {
                    Uri uri = App.getAppContext()
                            .getContentResolver()
                            .insert(MovieContract.MovieEntry.CONTENT_URI,
                                    mMovieEntityDataMapper.toContentValues(movie));
                    Log.v(LOG_TAG, "buildUseCaseObservable(): uri = " + uri);
                    Long id = MovieContract.MovieEntry.getMovieIdFromUri(uri);
                    subscriber.onNext(id);
                    subscriber.onCompleted();
                }
        );
    }

    @Override
    public Observable<Integer> deleteMovieEntity(long movieId) {
        String[] selectionArgs = new String[]{Long.toString(movieId)};
        Log.v(LOG_TAG, "deleteMovieEntity(): selectionArgs[0] = " + selectionArgs[0]);

        return Observable.<Integer>create(subscriber -> {
            int deleted =  App.getAppContext().getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    sMovieIdSelection,
                    selectionArgs);
            Log.v(LOG_TAG, "buildUseCaseObservable(): deleted = " + deleted);
            subscriber.onNext(deleted);
            subscriber.onCompleted();
        });
    }
}
