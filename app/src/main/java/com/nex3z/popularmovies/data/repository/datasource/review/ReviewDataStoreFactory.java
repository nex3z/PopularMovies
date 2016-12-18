package com.nex3z.popularmovies.data.repository.datasource.review;

import com.nex3z.popularmovies.data.net.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReviewDataStoreFactory {

    private RestClient mRestClient;

    @Inject
    public ReviewDataStoreFactory(RestClient restClient) {
        mRestClient = restClient;
    }

    public ReviewDataStore createCloudReviewDataStore() {
        return new CloudReviewDataStore(mRestClient);
    }
}
