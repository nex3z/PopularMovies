package com.nex3z.popularmovies.data.local;

import android.util.Log;

import com.nex3z.popularmovies.data.entity.movie.MovieEntity;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmMovieDataStore implements LocalMovieDataStore {
    private static final String LOG_TAG = RealmMovieDataStore.class.getSimpleName();

    @Override
    public Observable<Integer> addToFavourite(MovieEntity movieEntity) {
        return Observable.create(emitter -> {
            Realm realm = buildDefaultRealm();
            realm.beginTransaction();
            realm.copyToRealm(movieEntity);
            realm.commitTransaction();
            realm.close();

            emitter.onNext(1);
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
                    .equalTo("id", movieId)
                    .findAll();
            Log.v(LOG_TAG, "isFavourite(): movieId = " + movieId
                    + ", result size = " + result.size());
            if (result.size() > 0) {
                emitter.onNext(true);
            } else {
                emitter.onNext(false);
            }
            emitter.onComplete();
        });
    }

    private Realm buildDefaultRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        return Realm.getInstance(configuration);
    }

}
