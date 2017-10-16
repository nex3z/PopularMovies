package com.nex3z.popularmovies.presentation.util;

import android.util.TypedValue;

import com.nex3z.popularmovies.presentation.app.App;

public class ViewUtil {

    private ViewUtil() {}

    public static float dpToPx(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                App.getContext().getResources().getDisplayMetrics());
    }

}
