package com.hym.shop.ui.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration2 extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration2(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
//        outRect.left = space;
        outRect.bottom = space/2;
        outRect.top = space/2;
        //由于每行都只有2个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % 2 == 0 && parent.getChildLayoutPosition(view)>1) {
            outRect.left = space;
        }
    }
}
