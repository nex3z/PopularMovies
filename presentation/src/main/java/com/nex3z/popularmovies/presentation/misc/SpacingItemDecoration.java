package com.nex3z.popularmovies.presentation.misc;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpacingTop;
    private int mSpacingRight;
    private int mSpacingBottom;
    private int mSpacingLeft;

    public SpacingItemDecoration(int space) {
        this(space, space, space, space);
    }

    public SpacingItemDecoration(int top, int right, int bottom, int left) {
        mSpacingTop = top;
        mSpacingRight = right;
        mSpacingBottom = bottom;
        mSpacingLeft = left;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.top = mSpacingTop;
        outRect.right = mSpacingRight;
        outRect.bottom = mSpacingBottom;
        outRect.left = mSpacingLeft;
    }

}