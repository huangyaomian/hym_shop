package com.hym.shop.ui.fragment;


import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hym.shop.R;
import com.hym.shop.bean.HomeBean;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerHomeComponent;
import com.hym.shop.dagger2.module.HomeModule;
import com.hym.shop.presenter.HomePresenter;
import com.hym.shop.presenter.contract.AppInfoContract;
import com.hym.shop.ui.adapter.HomeAdapter;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

public class HomeFragment extends ProgressFragment<HomePresenter> implements AppInfoContract.View {


    @BindView(R.id.home_rv)
    RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerHomeComponent.builder().appComponent(appComponent).homeModule(new HomeModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void initView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }


    @Override
    protected void init() {

        //这里为了解决recycleview不能撑满全屏的问题，这里layoutManager不管你布局里是否设置，都不准确，所以需要在代码里
        //重新设置MATCH_PARENT
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

//        mHomeRv.setItemAnimator(new DefaultItemAnimator());
        mPresenter.requestHomeData(true);
    }

    @Override
    protected void initEvent() {

    }





    @Override
    public void showResult(HomeBean homeBean) {

        mAdapter = new HomeAdapter(getActivity(), homeBean);

        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        Log.d("showResult", String.valueOf(mRecyclerView.getChildCount()));

    }



    @Override
    public void showNoData() {

    }

    @Override
    public void onRequestPermissionSuccess() {

    }

    @Override
    public void onRequestPermissionError() {
        Toast.makeText(getActivity(),"您已拒絕授權!",Toast.LENGTH_SHORT).show();
    }


}
