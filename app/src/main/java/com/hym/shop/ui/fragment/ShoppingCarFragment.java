package com.hym.shop.ui.fragment;


import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerShoppingCarComponent;
import com.hym.shop.dagger2.module.ShoppingCarModule;
import com.hym.shop.presenter.ShoppingCarPresenter;
import com.hym.shop.presenter.contract.ShoppingCarContract;
import com.hym.shop.ui.adapter.ShoppingCarAdapter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class ShoppingCarFragment extends ProgressFragment<ShoppingCarPresenter> implements ShoppingCarContract.ShoppingCarView, ShoppingCarAdapter.StatusChangeListener {


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.checkbox_all)
    CheckBox mCheckboxAll;
    @BindView(R.id.txt_total)
    TextView mTxtTotal;
    @BindView(R.id.btn_order)
    Button mBtnOrder;
    @BindView(R.id.btn_del)
    Button mBtnDel;

    private ShoppingCarAdapter mShoppingCarAdapter;





    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerShoppingCarComponent.builder().appComponent(appComponent).shoppingCarModule(new ShoppingCarModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_shopping_car;
    }

    @Override
    protected void initView() {
        initRefresh();
    }


    @Override
    protected void init() {

        showToolBar();

        mShoppingCarAdapter = new ShoppingCarAdapter();
        mShoppingCarAdapter.setStatusChangeListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.getShoppingCarWares(true);

    }

    @Override
    protected void initEvent() {
        mCheckboxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (int i = 0; i < mShoppingCarAdapter.getData().size(); i++) {
                    mShoppingCarAdapter.getData().get(i).setCheck(b);
                }
                mShoppingCarAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRefresh() {
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getShoppingCarWares(false);
            }
        });

    }


    @Override
    public void showShoppingCar(List<HotWares.WaresBean> waresBeanList) {

        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh();
        }
        if (mShoppingCarAdapter.getData() != null && mShoppingCarAdapter.getData().size() > 0) {
            mShoppingCarAdapter.getData().clear();
        }

        mShoppingCarAdapter.addData(waresBeanList);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mShoppingCarAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));

        setAllCheckAndTotalPrice(waresBeanList);

    }

    private void setAllCheckAndTotalPrice(List<HotWares.WaresBean> waresBeanList){
        boolean isCheckAll = true;
        double totalPrice = 0.00;
        for (int i = 0; i < waresBeanList.size(); i++) {
            if (waresBeanList.get(i).isCheck()){
                totalPrice = totalPrice + (waresBeanList.get(i).getPrice() * waresBeanList.get(i).getCount());
            }else {
                isCheckAll=false;
            }
        }

        mCheckboxAll.setChecked(isCheckAll);
        mTxtTotal.setText("合計:" +totalPrice);
    }


    @Override
    public void statusChange() {
        List<HotWares.WaresBean> data = mShoppingCarAdapter.getData();
        setAllCheckAndTotalPrice(data);
    }
}
