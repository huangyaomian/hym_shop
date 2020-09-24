package com.hym.shop.ui.adapter;



import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.AlignSelf;
import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.Order;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.common.imageloader.ImageLoader;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.CallSite;

public class MyOrderAdapter extends BaseQuickAdapter<Order, BaseViewHolder>{


    public static final int STATUS_SUCCESS = 1; //支付成功的订单
    public static final int STATUS_PAY_FAIL = -2; //支付失败的订单
    public static final int STATUS_PAY_WAIT = 0; //：待支付的订单


    public MyOrderAdapter() {
        super(R.layout.order_item);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Order order) {
        ImageView imageView;
        for (int i = 0; i < order.getItems().size(); i++) {
            if (i==0){
                imageView =  baseViewHolder.getView(R.id.img_1);
                imageView.setVisibility(View.VISIBLE);
                ImageLoader.load(order.getItems().get(i).getWares().getImgUrl(),imageView);
            }else if (i==1){
                imageView =  baseViewHolder.getView(R.id.img_2);
                imageView.setVisibility(View.VISIBLE);
                ImageLoader.load(order.getItems().get(i).getWares().getImgUrl(),imageView);
            }else if (i==2){
                imageView =  baseViewHolder.getView(R.id.img_3);
                imageView.setVisibility(View.VISIBLE);
                ImageLoader.load(order.getItems().get(i).getWares().getImgUrl(),imageView);
                if (order.getItems().size()>3){
                    baseViewHolder.setText(R.id.img_num,  "+" + (order.getItems().size()-3));
                    imageView.setColorFilter(getContext().getResources().getColor(R.color.theme_grey_95));
                }
            }

        }
        baseViewHolder.setText(R.id.order_num,"订单号：" + order.getOrderNum());
        baseViewHolder.setText(R.id.order_prices,"实付金额：￥"+order.getAmount());

        if (order.getStatus() == STATUS_SUCCESS){
            baseViewHolder.setText(R.id.order_status,"支付成功");
        }else if (order.getStatus() == STATUS_PAY_FAIL){
            baseViewHolder.setText(R.id.order_status,"支付失败");
        }else if (order.getStatus() == STATUS_PAY_WAIT){
            baseViewHolder.setText(R.id.order_status,"待支付");
        }
    }





}
