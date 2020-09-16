package com.hym.shop.ui.fragment;


import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.diff.BrvahAsyncDiffer;
import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerShoppingCarComponent;
import com.hym.shop.dagger2.module.ShoppingCarModule;
import com.hym.shop.presenter.ShoppingCarPresenter;
import com.hym.shop.presenter.contract.ShoppingCarContract;
import com.hym.shop.ui.activity.MainActivity;
import com.hym.shop.ui.activity.SearchActivity;
import com.hym.shop.ui.adapter.ShoppingCarAdapter;
import com.hym.shop.ui.widget.BadgeActionProvider;
import com.hym.shop.ui.widget.MyDialog;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
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

    private final int  PAGE_STATUS_NORMAL = 1;
    private final int  PAGE_STATUS_EDIT = 2;

    public int pageStatus = PAGE_STATUS_NORMAL;



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
        ((AppCompatActivity) getActivity()).setSupportActionBar(getToolBar());

        showToolBar();

        setToolBarTitle("购物车");

        setHasOptionsMenu(true);

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

        mCheckboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckboxAll.isChecked()) {
                    for (int i = 0; i < mShoppingCarAdapter.getData().size(); i++) {
                        mShoppingCarAdapter.getData().get(i).setCheck(true);
                    }
                }else {
                    for (int i = 0; i < mShoppingCarAdapter.getData().size(); i++) {
                        mShoppingCarAdapter.getData().get(i).setCheck(false);
                    }
                }
                mShoppingCarAdapter.notifyDataSetChanged();
            }
        });

        getToolBar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.upper_right_corner:
                        if (pageStatus == PAGE_STATUS_NORMAL){
                            setPageEdit();

                        }else {
                            setPageNormal();
                        }
                        break;
                }
                return true;
            }
        });

        mBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mShoppingCarAdapter.getData().size(); i++) {
                    if (mShoppingCarAdapter.getData().get(i).isCheck()) {
                        MyDialog.show(getActivity(), "確認刪除？", new MyDialog.OnConfirmListener() {
                            @Override
                            public void onConfirmClick() {
                                for (int i = 0; i < mShoppingCarAdapter.getData().size(); i++) {
                                    if (mShoppingCarAdapter.getData().get(i).isCheck()){
                                        DBManager.DeleteWares(mShoppingCarAdapter.getData().get(i));
                                    }
                                }
                                setPageNormal();
                            }
                        });
                        return;
                    }
                }
                Toast.makeText(getContext(),"請選擇刪除的商品！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPageEdit(){
        pageStatus = PAGE_STATUS_EDIT;
        mShoppingCarAdapter.setPageStatus(PAGE_STATUS_EDIT);
        mTxtTotal.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.GONE);
        mSmartRefreshLayout.setEnableRefresh(false);
        for (int i = 0; i < mShoppingCarAdapter.getData().size(); i++) {
            mShoppingCarAdapter.getData().get(i).setCheck(false);
        }
        mShoppingCarAdapter.notifyDataSetChanged();
        if (getToolBar().getMenu().getItem(0).getItemId() == R.id.upper_right_corner) {
            getToolBar().getMenu().getItem(0).setTitle("取消");
        }
    }

    private void setPageNormal(){
        pageStatus = PAGE_STATUS_NORMAL;
        mShoppingCarAdapter.setPageStatus(PAGE_STATUS_NORMAL);
        mTxtTotal.setVisibility(View.VISIBLE);
        mBtnDel.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mSmartRefreshLayout.setEnableRefresh(true);
        mPresenter.getShoppingCarWares(true);
        getToolBar().getMenu().getItem(0).setTitle("编辑");
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
    public void statusChange(boolean isCheck) {
        List<HotWares.WaresBean> data = mShoppingCarAdapter.getData();
        setAllCheckAndTotalPrice(data);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getShoppingCarWares(true);
        if (pageStatus == PAGE_STATUS_EDIT){
            setPageNormal();
        }
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
//        menu.findItem(R.id.upper_right_corner).setIcon(new IconicsDrawable(getContext(), Ionicons.Icon.ion_edit).color(getResources().getColor(R.color.TextColor)).actionBar());
    }



}
