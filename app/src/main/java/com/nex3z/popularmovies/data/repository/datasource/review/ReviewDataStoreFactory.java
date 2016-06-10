package com.nex3z.popularmovies.data.repository.datasource.review;

import com.nex3z.popularmovies.app.App;

public class ReviewDataStoreFactory {
    public ReviewDataStore createCloudReviewDataStore() {
        return new CloudReviewDataStore(App.getRestClient());
    }
}
