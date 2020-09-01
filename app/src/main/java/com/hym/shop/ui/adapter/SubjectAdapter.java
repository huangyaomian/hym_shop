package com.hym.shop.ui.adapter;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.Subject;
import com.hym.shop.common.Constant;
import com.hym.shop.common.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class SubjectAdapter extends BaseQuickAdapter<Subject, BaseViewHolder> implements LoadMoreModule {


    public SubjectAdapter() {
        super(R.layout.template_imageview);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Subject subject) {
        ImageLoader.load(Constant.BASE_IMG_URL +subject.getMticon(),baseViewHolder.getView(R.id.image_view));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    @Override
//    public void onBindViewHolder(@NotNull BaseViewHolder holder, int position) {
//        holder.setIsRecyclable(false);
//        super.onBindViewHolder(holder, position);
//    }
}
