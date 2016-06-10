package com.nex3z.popularmovies.domain.interactor.movie;

public class GetFavouriteMovieListArg {
    public static final String SORT_BY_ADD_DATE_DESC = "add_date_desc";

    private final String mSortBy;

    public GetFavouriteMovieListArg(String sortBy) {
        mSortBy = sortBy;
    }

    public GetFavouriteMovieListArg() {
        this(SORT_BY_ADD_DATE_DESC);
    }

    public String getSortBy() {
        return mSortBy;
    }

}
