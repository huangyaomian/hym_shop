package com.hym.shop.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.hym.shop.R;
import com.hym.shop.bean.Address;
import com.hym.shop.bean.Banner;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.common.imageloader.ImageLoader;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerAddressComponent;
import com.hym.shop.dagger2.module.AddressModule;
import com.hym.shop.presenter.AddressPresenter;
import com.hym.shop.presenter.contract.AddressContract;
import com.hym.shop.ui.adapter.AddressAdapter;
import com.hym.shop.ui.widget.MyDialog;
import com.hym.shop.ui.widget.SpaceItemDecoration4;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.ScaleInTransformer;

import java.util.IdentityHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class AddressListActivity extends ProgressActivity<AddressPresenter> implements AddressContract.AddressView {


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SmartRefreshLayout mSwipeRefresh;
    private AddressAdapter mAddressAdapter;
    private long mUserId;
    private String mToken;
    private int mDelPosition;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAddressComponent.builder().appComponent(appComponent).addressModule(new AddressModule(this)).build().injectList(this);
    }

    @Override
    public void init() {
        getUser();
        setShowToolBarBack(true);
        mAddressAdapter = new AddressAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItemDecoration4 dividerDecoration = new SpaceItemDecoration4(UIUtils.dp2px(8));
        mRecyclerView.addItemDecoration(dividerDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.showAddressList(mUserId,mToken,true);
    }

    private void  initRefresh() {
        mSwipeRefresh.setRefreshHeader(new ClassicsHeader(this));
        mSwipeRefresh.setEnableLoadMore(false);
        mSwipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.showAddressList(mUserId,mToken,false);
            }
        });
    }

    @Override
    public void initView() {
        setToolBarTitle("收貨地址");
        initRefresh();
    }

    @Override
    public void initEvent() {
        mAddressAdapter.addChildClickViewIds(R.id.radio_selected, R.id.radio_selectedText, R.id.delete_address_item, R.id.edit_address_item);

        mAddressAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mDelPosition = position;
                switch (view.getId()){
                    case R.id.radio_selected:
                    case R.id.radio_selectedText:
                        Address address = mAddressAdapter.getData().get(position);
                        mPresenter.updateAddress(address.getId(),address.getConsignee(),address.getPhone(),address.getAddr(),address.getZip_code(),true,mToken,false);
                        break;
                    case R.id.delete_address_item:
                        MyDialog.show(AddressListActivity.this, "確認刪除？", new MyDialog.OnConfirmListener() {
                            @Override
                            public void onConfirmClick() {
                                mPresenter.delAddress(mAddressAdapter.getData().get(position).getId(),mToken,false);
                            }
                        });
                        break;
                    case R.id.edit_address_item:
                        break;
                }
            }
        });
    }


    @Override
    public void showAddressList(List<Address> addressList) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.finishRefresh();
        }

        if (mAddressAdapter.getData() != null && mAddressAdapter.getData().size()>0){
            mAddressAdapter.getData().clear();
        }
        mAddressAdapter.addData(addressList);
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAddressAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));

    }

    @Override
    public void addAddress(BaseBean baseBean) {

    }

    @Override
    public void updateAddress(BaseBean baseBean) {
        if (baseBean.success()){
            mAddressAdapter.setRadioBtn(mDelPosition);
        }
        mDelPosition = 0;
    }

    @Override
    public void delAddress(BaseBean baseBean) {
        if (baseBean.success()){
            mAddressAdapter.removeAt(mDelPosition);
        }
        mDelPosition = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        menu.findItem(R.id.upper_right_corner).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_android_add).color(getResources().getColor(R.color.TextColor)).actionBar());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upper_right_corner:
                startActivity(new Intent(AddressListActivity.this, CreateAddressActivity.class));
                break;
        }
        return true;
    }

    private void getUser(){

        User user = (User) ACache.get(this).getAsObject(Constant.USER);

        mToken = ACache.get(this).getAsString(Constant.TOKEN);

        mUserId = user.getId();
    }
}
