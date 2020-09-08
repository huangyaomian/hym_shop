package com.hym.shop.ui.adapter;



import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.Category;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class CategoryAdapter extends BaseQuickAdapter<Category, BaseViewHolder>{


    public CategoryAdapter() {
        super(R.layout.template_category);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Category category) {
//        baseViewHolder.setIsRecyclable(false);
        baseViewHolder.setText(R.id.template_category_title,category.getName());
        if (category.isSelect()){
            baseViewHolder.setBackgroundColor(R.id.constraint_layout, Color.parseColor("#ffffff"));
            TextView view = baseViewHolder.getView(R.id.template_category_title);
            view.setTextColor(Color.parseColor("#00c6e6"));
            view.setTextSize(18);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }




}
