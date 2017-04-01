package com.nex3z.popularmovies.presentation.view;

public interface BaseView {

    void showMessage(String msg);

    void showMessage(int msgResId);

    void showError(String msg);

    void showError(int msgResId);

}
