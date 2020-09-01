package com.hym.shop.ui.adapter;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.common.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class screenShortAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public screenShortAdapter() {
        super(R.layout.template_imageview_vertical);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String url) {

        ImageLoader.load(url,baseViewHolder.getView(R.id.image_view));
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }




}
