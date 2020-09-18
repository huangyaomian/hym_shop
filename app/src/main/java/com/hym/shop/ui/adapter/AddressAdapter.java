package com.hym.shop.ui.adapter;



import android.os.strictmode.Violation;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hym.shop.R;
import com.hym.shop.bean.Address;

import org.jetbrains.annotations.NotNull;

public class AddressAdapter extends BaseQuickAdapter<Address, BaseViewHolder>{


    public AddressAdapter() {
        super(R.layout.address_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, Address address) {
        holder.setText(R.id.consignee_name,address.getConsignee());
        holder.setText(R.id.consignee_phone,replacePhoneNum(address.getPhone()));
        holder.setText(R.id.consignee_address,address.getAddr());

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public String replacePhoneNum(String phone) {

//        return phone.substring(0, phone.length() - (phone.substring(3)).length()) + "****" + phone.substring(7);
        return phone;
    }

    public void setRadioBtn(int position){
        for (int i = 0; i < getData().size(); i++) {
            if (i == position) {
                getData().get(i).setIsDefault(true);
            }else {
                getData().get(i).setIsDefault(false);
            }
        }
        notifyDataSetChanged();
    }





}
