package com.nex3z.popularmovies.data.repository.datasource;

import com.nex3z.popularmovies.app.App;

public class MovieDataStoreFactory {

    public MovieDataStore createCloudMovieDataStore() {
        return new CloudMovieDataStore(App.getRestClient());
    }
}
