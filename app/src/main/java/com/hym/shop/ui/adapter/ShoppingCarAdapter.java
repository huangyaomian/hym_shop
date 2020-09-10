package com.hym.shop.ui.adapter;



import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.common.imageloader.ImageLoader;
import com.hym.shop.ui.widget.WaresAddAndSubView;

import org.jetbrains.annotations.NotNull;

public class ShoppingCarAdapter extends BaseQuickAdapter<HotWares.WaresBean, BaseViewHolder>{

    private StatusChangeListener mStatusChangeListener;

    public interface StatusChangeListener{
        void statusChange(boolean isCheck);
    }

    public void setStatusChangeListener(StatusChangeListener listener){
        this.mStatusChangeListener =listener;
    }

    public ShoppingCarAdapter() {
        super(R.layout.template_car_wares);
    }


    private final int  PAGE_STATUS_NORMAL = 1;
    private final int  PAGE_STATUS_EDIT = 2;

    public int pageStatus = PAGE_STATUS_NORMAL;


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HotWares.WaresBean waresBean) {
        ImageLoader.load(waresBean.getImgUrl(),baseViewHolder.getView(R.id.image_view));
        CheckBox checkBox = baseViewHolder.getView(R.id.car_checkbox);
        checkBox.setChecked(waresBean.isCheck());
        baseViewHolder.setText(R.id.text_title,waresBean.getName());
        baseViewHolder.setText(R.id.text_price,"￥"+waresBean.getPrice());
        WaresAddAndSubView waresAddAndSubView = baseViewHolder.getView(R.id.car_add_and_sub);
        waresAddAndSubView.setValue(waresBean.getCount());
        waresAddAndSubView.setOnValueChangeListene(new WaresAddAndSubView.OnValueChangeListener() {
            @Override
            public void onValueChange(int value) {
                DBManager.updateWaresCount(waresBean.getId(),value);
                waresBean.setCount(value);
                mStatusChangeListener.statusChange(false);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!(pageStatus == PAGE_STATUS_EDIT)){
                    DBManager.updateWaresIsCheck(waresBean.getId(),b?1:0);
                }
                waresBean.setCheck(b);
                mStatusChangeListener.statusChange(b);

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void setPageStatus(int pageStatus){
       this.pageStatus = pageStatus;
    }




}
