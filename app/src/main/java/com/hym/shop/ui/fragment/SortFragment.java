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
import com.hym.shop.R;
import com.hym.shop.bean.SortBean;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.rx.RxBus;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerSortComponent;
import com.hym.shop.dagger2.module.SortModule;
import com.hym.shop.presenter.SortPresenter;
import com.hym.shop.presenter.contract.SortContract;
import com.hym.shop.ui.activity.SortAppActivity;
import com.hym.shop.ui.adapter.SortAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;


public class SortFragment extends ProgressFragment<SortPresenter> implements SortContract.SortView {


    @BindView(R.id.home_rv)
    RecyclerView mHomeRv;

    protected SortAdapter mSortAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSortComponent.builder().appComponent(appComponent).sortModule(new SortModule(this)).build().inject(this);
    }

    @Override
    protected void init() {
        RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {
                mPresenter.getAllSort();
            }
        });
        mPresenter.getAllSort();
    }

    @Override
    protected void initView() {
        mHomeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.shape_question_diveder));
        mHomeRv.addItemDecoration(dividerItemDecoration);
        mSortAdapter = new SortAdapter();

        mHomeRv.setAdapter(mSortAdapter);
        mSortAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), SortAppActivity.class);
                intent.putExtra(Constant.CATEGORY, mSortAdapter.getData().get(position));
                startActivity(intent);
        }
        });

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void showResult(List<SortBean> datas) {
        mSortAdapter.addData(datas);
    }
}
