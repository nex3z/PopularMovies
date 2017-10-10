package com.nex3z.popularmovies.data.entity.discover;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

public class DiscoverMovieParams {

    public static final String SORT_BY_RELEASE_DATE_DESC = "release_date.desc";
    public static final String SORT_BY_POPULARITY_DESC = "popularity.desc";
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({SORT_BY_RELEASE_DATE_DESC, SORT_BY_POPULARITY_DESC})
    public @interface SortBy {}

    private final Map<String, String> mParams;

    private DiscoverMovieParams(Map<String, String> params) {
        mParams = params;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public static class Builder {
        private final Map<String, String> mParams = new HashMap<>();

        public Builder page(int page) {
            mParams.put("page", String.valueOf(page));
            return this;
        }

        public Builder sortBy(@SortBy String sortBy) {
            mParams.put("sort_by", String.valueOf(sortBy));
            return this;
        }

        public DiscoverMovieParams build() {
            return new DiscoverMovieParams(mParams);
        }
    }
}
