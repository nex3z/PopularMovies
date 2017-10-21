package com.nex3z.popularmovies.presentation.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    public void onResume() {
        super.onResume();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().destroy();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
