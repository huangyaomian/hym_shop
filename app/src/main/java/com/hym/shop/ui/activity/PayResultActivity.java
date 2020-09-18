package com.hym.shop.ui.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.hym.shop.R;
import com.hym.shop.common.Constant;
import com.hym.shop.dagger2.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayResultActivity extends ProgressActivity {


    @BindView(R.id.img_pay)
    ImageView imgPay;
    @BindView(R.id.tv_pay_result)
    TextView tvPayResult;
    @BindView(R.id.btn_back_home)
    Button btnBackHome;

    private int mPayStatus;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }


    @Override
    public void init() {
        mPayStatus = getIntent().getIntExtra(Constant.PAY_STATUS, 0);
        setShowToolBarBack(true);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        setToolBarTitle("支付結果");
    }


    @Override
    public void initView() {
        if (mPayStatus == Constant.SUCCESS){
            tvPayResult.setText("支付成功");
            imgPay.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_pay_success));
        }else if(mPayStatus == Constant.CANCEL){
            tvPayResult.setText("用户取消支付");
            imgPay.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_pay_fail));
        }else if(mPayStatus == Constant.INVALID){
            tvPayResult.setText("用户未安装支付APP");
            imgPay.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_pay_fail));
        }else {
            //支付失败
            tvPayResult.setText("支付失败");
            imgPay.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_pay_fail));
        }
    }


    @Override
    public void initEvent() {

    }


}
