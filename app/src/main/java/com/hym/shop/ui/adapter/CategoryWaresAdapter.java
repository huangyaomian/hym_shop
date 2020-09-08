package com.hym.shop.ui.adapter;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class CategoryWaresAdapter extends BaseQuickAdapter<HotWares.WaresBean, BaseViewHolder>{


    public CategoryWaresAdapter() {
        super(R.layout.template_category_wares);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HotWares.WaresBean waresBean) {
        ImageLoader.load(waresBean.getImgUrl(),baseViewHolder.getView(R.id.image_view));
        baseViewHolder.setText(R.id.text_title,waresBean.getName());
        baseViewHolder.setText(R.id.text_price,"￥"+waresBean.getPrice());
    }




}
