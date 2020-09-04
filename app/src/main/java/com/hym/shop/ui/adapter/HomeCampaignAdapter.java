package com.hym.shop.ui.adapter;


import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.common.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeCampaignAdapter extends BaseDelegateMultiAdapter<HomeCampaign, BaseViewHolder> {
    public static final int CARD_VIEW_LEFT = 2;
    public static final int CARD_VIEW_RIGHT = 1;

    public HomeCampaignAdapter() {
        super();
        // 第一步，设置代理
        setMultiTypeDelegate(new BaseMultiTypeDelegate<HomeCampaign>() {
            @Override
            public int getItemType(@NotNull List<? extends HomeCampaign> data, int position) {
                // 根据数据，自己判断应该返回的类型
                if (position % 2 == 0){
                    return CARD_VIEW_LEFT;
                }else {
                    return CARD_VIEW_RIGHT;
                }

            }
        });

        // 第二部，绑定 item 类型
        getMultiTypeDelegate()
                .addItemType(CARD_VIEW_LEFT, R.layout.template_cardview_left)
                .addItemType(CARD_VIEW_RIGHT, R.layout.template_cardview_right);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeCampaign item) {
        baseViewHolder.setText(R.id.text_title,item.getTitle());
        ImageLoader.load(item.getCpOne().getImgUrl(),baseViewHolder.getView(R.id.img_view_big));
        ImageLoader.load(item.getCpTwo().getImgUrl(),baseViewHolder.getView(R.id.img_view_small_top));
        ImageLoader.load(item.getCpThree().getImgUrl(),baseViewHolder.getView(R.id.img_view_small_bottom));

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
