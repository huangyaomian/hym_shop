package com.hym.shop.ui.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration4 extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration4(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space/2;
        outRect.right = space/2;
        outRect.bottom = space;
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        }
    }
}
