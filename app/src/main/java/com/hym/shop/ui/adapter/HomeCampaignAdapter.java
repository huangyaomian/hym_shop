package com.hym.shop.ui.adapter;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.HomeCampaign;
import com.hym.shop.common.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeCampaignAdapter extends BaseMultiItemQuickAdapter<HomeCampaignAdapter.MultipleItem, BaseViewHolder> {


    public HomeCampaignAdapter(List data) {
        super(data);
        addItemType(MultipleItem.CARD_VIEW_LEFT, R.layout.template_cardview_left);
        addItemType(MultipleItem.CARD_VIEW_RIGHT, R.layout.template_cardview_right);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MultipleItem item) {
        ImageLoader.load(item.g(),baseViewHolder.getView(R.id.sort_item_iv));
        baseViewHolder.setText(R.id.sort_recyclerview_name,sortBean.getName());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MultipleItem implements MultiItemEntity {
        public static final int CARD_VIEW_LEFT = 1;
        public static final int CARD_VIEW_RIGHT = 2;
        private int itemType;
        public MultipleItem(int itemType) {
            this.itemType = itemType;
        }
        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
