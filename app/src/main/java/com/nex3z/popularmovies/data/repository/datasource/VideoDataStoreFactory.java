package com.nex3z.popularmovies.data.repository.datasource;

import com.nex3z.popularmovies.app.App;

public class VideoDataStoreFactory {

    public VideoDataStore createCloudVideoDataStore() {
        return new CloudVideoDataStore(App.getRestClient());
    }

}
