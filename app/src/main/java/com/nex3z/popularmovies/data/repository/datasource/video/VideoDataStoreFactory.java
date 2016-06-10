package com.nex3z.popularmovies.data.repository.datasource.video;

import com.nex3z.popularmovies.app.App;

public class VideoDataStoreFactory {

    public VideoDataStore createCloudVideoDataStore() {
        return new CloudVideoDataStore(App.getRestClient());
    }

}
