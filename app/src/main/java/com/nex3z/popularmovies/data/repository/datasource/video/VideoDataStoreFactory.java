package com.nex3z.popularmovies.data.repository.datasource.video;

import com.nex3z.popularmovies.data.net.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VideoDataStoreFactory {

    private RestClient mRestClient;

    @Inject
    public VideoDataStoreFactory(RestClient restClient) {
        mRestClient = restClient;
    }

    public VideoDataStore createCloudVideoDataStore() {
        return new CloudVideoDataStore(mRestClient);
    }

}
