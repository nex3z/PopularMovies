package com.nex3z.popularmovies.util;


import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

public class UiUtility {

    public static void hideStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static void showStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(color);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateStatusBarAlpha(Activity activity, int color, float fromAlpha, float toAlpha) {
        Window window = activity.getWindow();

        ValueAnimator animator = ValueAnimator.ofFloat(fromAlpha, toAlpha).setDuration(500);
        animator.addUpdateListener(animation -> {
                float alpha = (float) animation.getAnimatedValue();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ScrollUtils.getColorWithAlpha(alpha, color));
                }
            }
        );
        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            int color = window.getStatusBarColor();
            animateStatusBarAlpha(activity, color, 1, 0);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateStatusBarSolid(Activity activity, int color) {
        animateStatusBarAlpha(activity, color, 0, 1);
    }

    public static void animateViewAlpha(View view, int color, float fromAlpha, float toAlpha) {
        ValueAnimator animator = ValueAnimator.ofFloat(fromAlpha, toAlpha).setDuration(500);
        animator.addUpdateListener(animation -> {
                float alpha = (float) animation.getAnimatedValue();
                int colorWithAlpha = ScrollUtils.getColorWithAlpha(alpha, color);
                view.setBackgroundColor(colorWithAlpha);
            }
        );
        animator.start();
    }

    public static void animateViewTransparent(View view, int color) {
        animateViewAlpha(view, color, 1, 0);
    }

    public static void animateViewSolid(View view, int color) {
        animateViewAlpha(view, color, 0, 1);
    }

}
