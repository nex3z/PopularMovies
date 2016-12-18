package com.nex3z.popularmovies.data.repository.datasource.movie;

import com.nex3z.popularmovies.data.net.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MovieDataStoreFactory {
    private RestClient mRestClient;

    @Inject
    public MovieDataStoreFactory(RestClient restClient) {
        mRestClient = restClient;
    }

    public MovieDataStore createCloudMovieDataStore() {
        return new CloudMovieDataStore(mRestClient);
    }

    public MovieDataStore createContentProviderDataStore() {
        return new ContentProviderMovieDataStore();
    }

}
