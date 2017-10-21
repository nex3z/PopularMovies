package com.nex3z.popularmovies.presentation.base;

public interface HasPresenter<T extends Presenter> {

    T getPresenter();

}
