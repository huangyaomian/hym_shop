package com.hym.shop.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hym.shop.R;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.Charge;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.OrderRespMsg;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerCreateOrderComponent;
import com.hym.shop.dagger2.module.CreateOrderModule;
import com.hym.shop.presenter.CreateOrderPresenter;
import com.hym.shop.presenter.contract.CreateOrderContract;
import com.hym.shop.ui.adapter.CreateOrderWaresAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration4;
import com.pingplusplus.android.Pingpp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

public class PayResultActivity extends ProgressActivity {





    @Override
    protected int setLayoutResourceID() {
        return R.layout.;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }


    @Override
    public void init() {
        setShowToolBarBack(true);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        setToolBarTitle("支付結果");
    }


    @Override
    public void initView() {
    }


    @Override
    public void initEvent() {

    }








}
