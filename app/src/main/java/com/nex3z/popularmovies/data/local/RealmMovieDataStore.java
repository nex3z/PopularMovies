package com.nex3z.popularmovies.data.local;

import android.util.Log;

import com.nex3z.popularmovies.data.entity.movie.MovieEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class RealmMovieDataStore implements LocalMovieDataStore {
    private static final String LOG_TAG = RealmMovieDataStore.class.getSimpleName();

    @Override
    public Observable<Integer> addToFavourite(MovieEntity movieEntity) {
        return Observable.create(emitter -> {
            Realm realm = buildDefaultRealm();
            //noinspection TryFinallyCanBeTryWithResources
            try {
                realm.beginTransaction();
                realm.copyToRealm(movieEntity);
                realm.commitTransaction();
            } catch (RealmPrimaryKeyConstraintException exception) {
                emitter.onError(new RuntimeException("The movie is already set to favourite."));
            } finally {
                realm.close();
            }
            emitter.onNext(1);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<Integer> removeFromFavourite(long movieId) {
        return Observable.create(emitter -> {
            Realm realm = buildDefaultRealm();
            RealmResults<MovieEntity> result = realm.where(MovieEntity.class)
                    .equalTo("mId", movieId)
                    .findAll();
            Log.v(LOG_TAG, "isFavourite(): movieId = " + movieId
                    + ", result size = " + result.size());
            if (result.size() > 0) {
                realm.beginTransaction();
                result.deleteAllFromRealm();
                realm.commitTransaction();
                emitter.onNext(1);
            } else {
                emitter.onError(new RuntimeException("The movie is not set to favourite."));
            }
            realm.close();
            emitter.onComplete();
        });
    }

    @Override
    public Observable<List<MovieEntity>> getFavouriteMovies() {
        return Observable.create(emitter  -> {
            Realm realm = buildDefaultRealm();
            RealmResults<MovieEntity> result = realm
                    .where(MovieEntity.class)
                    .findAll();
            emitter.onNext(realm.copyFromRealm(result));
            realm.close();

            emitter.onComplete();
        });
    }

    @Override
    public Observable<Boolean> isFavourite(long movieId) {
        return Observable.create(emitter -> {
            Realm realm = buildDefaultRealm();
            RealmResults<MovieEntity> result = realm.where(MovieEntity.class)
                    .equalTo("mId", movieId)
                    .findAll();
            Log.v(LOG_TAG, "isFavourite(): movieId = " + movieId
                    + ", result size = " + result.size());
            if (result.size() > 0) {
                emitter.onNext(true);
            } else {
                emitter.onNext(false);
            }
            realm.close();
            emitter.onComplete();
        });
    }

    @Override
    public Observable<List<Boolean>> isFavourite(List<Long> movieIds) {
        Log.v(LOG_TAG, "isFavourite(): movieIds = " + movieIds);
        return Observable.create(emitter -> {
            Realm realm = buildDefaultRealm();
            RealmQuery<MovieEntity> query = realm.where(MovieEntity.class);
            for (long id : movieIds) {
                query.or().equalTo("mId", id);
            }
            RealmResults<MovieEntity> result = query.findAll();

            List<Boolean> favourite = new ArrayList<>(Arrays.asList(new Boolean[movieIds.size()]));
            Collections.fill(favourite, Boolean.FALSE);
            for (int i = 0; i < result.size(); i++) {
                int position = movieIds.indexOf(result.get(i).getId());
                Log.v(LOG_TAG, "isFavourite(): id = " + result.get(i).getId() + ", position = " + position);
                favourite.set(position, true);
            }
            Log.v(LOG_TAG, "isFavourite(): favourite = " + favourite);
            emitter.onNext(favourite);
            realm.close();
            emitter.onComplete();
        });
    }

    private Realm buildDefaultRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        return Realm.getInstance(configuration);
    }

}
