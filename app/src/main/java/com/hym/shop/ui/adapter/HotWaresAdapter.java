package com.hym.shop.ui.adapter;



import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.SortBean;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.common.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;

public class HotWaresAdapter extends BaseQuickAdapter<HotWares.WaresBean, BaseViewHolder>{


    public HotWaresAdapter() {
        super(R.layout.template_hot_wares);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HotWares.WaresBean waresBean) {
        ImageLoader.load(waresBean.getImgUrl(),baseViewHolder.getView(R.id.image_view));
        baseViewHolder.setText(R.id.text_title,waresBean.getName());
        baseViewHolder.setText(R.id.text_price,"￥"+waresBean.getPrice());
        baseViewHolder.getView(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager.insertWares(waresBean);
                Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
            }
        });
    }




}
