package com.nex3z.popularmovies.presentation.base;

import android.support.annotation.CallSuper;

public abstract class BasePresenter<T extends BaseView> implements Presenter {

    protected T mView;

    public void setView(T view) {
        mView = view;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override @CallSuper
    public void destroy() {
        mView = null;
    }

    protected T getView() {
        return mView;
    }

}
