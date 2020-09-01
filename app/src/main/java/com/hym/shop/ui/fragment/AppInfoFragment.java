package com.hym.shop.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.hym.shop.R;
import com.hym.shop.bean.AppInfoBean;
import com.hym.shop.bean.PageBean;
import com.hym.shop.bean.User;
import com.hym.shop.common.rx.RxBus;
import com.hym.shop.presenter.AppInfoPresenter;
import com.hym.shop.presenter.contract.AppInfoContract;
import com.hym.shop.ui.activity.AppDetailsActivity3;
import com.hym.shop.ui.adapter.AppInfoAdapter;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public abstract class AppInfoFragment extends ProgressFragment<AppInfoPresenter> implements AppInfoContract.AppInfoView {

    @BindView(R.id.home_rv)
    RecyclerView mHomeRv;

    int page = 0;

    protected AppInfoAdapter mAppInfoAdapter;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void init() {
        RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {
                mPresenter.requestData(setType(),page);
            }
        });
        mPresenter.requestData(setType(),page);
        initRecyclerView();
    }

    protected void initRecyclerView(){
        mHomeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.shape_question_diveder));
        mHomeRv.addItemDecoration(dividerItemDecoration);
        mAppInfoAdapter = buildAdapter();
        mAppInfoAdapter.setAnimationEnable(true);
        mAppInfoAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        mHomeRv.setAdapter(mAppInfoAdapter);
    }

    abstract AppInfoAdapter buildAdapter();

    abstract int setType();

    @Override
    protected void initEvent() {
        mAppInfoAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.requestData(setType(),page);
            }
        });


        // 设置点击事件
        mAppInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                mMyApplication.setView(view);
                AppInfoBean appInfoBean = mAppInfoAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), AppDetailsActivity3.class);
                intent.putExtra("appInfo",appInfoBean);
                startActivity(intent);
            }
        });
    }


    @Override
    public void showResult(PageBean<AppInfoBean> data) {
        mAppInfoAdapter.addData(data.getDatas());
        if (data.isHasMore()){
            page++;
        }
        mAppInfoAdapter.getLoadMoreModule().setEnableLoadMore(data.isHasMore());
    }

    @Override
    public void onLoadMoreComplete() {
// 当前这次数据加载完毕，调用此方法
        mAppInfoAdapter.getLoadMoreModule().loadMoreComplete();
    }

}
