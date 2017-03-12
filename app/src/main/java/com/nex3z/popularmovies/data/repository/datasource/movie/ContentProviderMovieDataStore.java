package com.nex3z.popularmovies.data.repository.datasource.movie;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.entity.MovieEntity;
import com.nex3z.popularmovies.data.entity.mapper.MovieCursorDataMapper;
import com.nex3z.popularmovies.data.entity.mapper.MovieEntityDataMapper;
import com.nex3z.popularmovies.data.provider.MovieContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ContentProviderMovieDataStore implements MovieDataStore {
    private static final String LOG_TAG = ContentProviderMovieDataStore.class.getSimpleName();

    private static final String sMovieIdSelection = MovieContract.MovieEntry.TABLE_NAME + "." +
            MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?";

    private final MovieCursorDataMapper mMovieCursorDataMapper;
    private final MovieEntityDataMapper mMovieEntityDataMapper;


    public ContentProviderMovieDataStore() {
        mMovieCursorDataMapper = new MovieCursorDataMapper();
        mMovieEntityDataMapper = new MovieEntityDataMapper();
    }

    @Override
    public Observable<List<MovieEntity>> getMovieEntityList(String sortBy) {
        return Observable.create(emitter -> {
            Cursor cursor = App.getAppContext().getContentResolver()
                    .query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
            emitter.onNext(mMovieCursorDataMapper.transformList(cursor));
            emitter.onComplete();
        });

//        return mBriteContentResolver
//                .createQuery(
//                        MovieContract.MovieEntry.CONTENT_URI,
//                        null,
//                        null,
//                        null,
//                        null,
//                        true)
//                .map(SqlBrite.Query::run)
//                .map(mMovieCursorDataMapper::transformList);
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
                    subscriber.onComplete();
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
            subscriber.onComplete();
        });
    }

    @Override
    public Observable<List<Boolean>> checkFavourite(List<Long> movieIds) {
        return Observable.<List<Boolean>>create(subscriber -> {
            List<Boolean> result = new ArrayList<Boolean>();
            for (Long movieId : movieIds) {
                String[] selectionArgs = new String[]{Long.toString(movieId)};
                Cursor cursor = App.getAppContext()
                        .getContentResolver()
                        .query(MovieContract.MovieEntry.CONTENT_URI,
                                null,
                                sMovieIdSelection,
                                selectionArgs,
                                null);
                if (cursor != null && cursor.moveToFirst()) {
                    result.add(true);
                } else {
                    result.add(false);
                }
            }
            subscriber.onNext(result);
            subscriber.onComplete();
        });
    }
}
