package com.hym.shop.ui.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hym.shop.R;
import com.hym.shop.bean.Subject;
import com.hym.shop.bean.SubjectDetail;
import com.hym.shop.common.Constant;
import com.hym.shop.common.imageloader.ImageLoader;
import com.hym.shop.ui.adapter.AppInfoAdapter;

import butterknife.BindView;


public class SubjectDetailFragment extends BaseSubjectFragment {

    @BindView(R.id.imageview)
    ImageView mImageView;
    @BindView(R.id.txt_desc)
    TextView mtxtDesc;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    private Subject mSubject;

    private AppInfoAdapter mAdapter;

    public SubjectDetailFragment() {

    }

    public SubjectDetailFragment setArgs(Subject subject) {
        this.mSubject = subject;
        return this;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_subject_detail;
    }


    @Override
    protected void init() {
        initRecycleView();
        mPresenter.getSubjectDetail(mSubject.getRelatedId());
    }

    private void initRecycleView() {
        mAdapter = AppInfoAdapter.builder().showBrief(false).showCategoryName(true)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);

        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showSubjectDetail(SubjectDetail detail) {
        ImageLoader.load(Constant.BASE_IMG_URL + detail.getPhoneBigIcon(), mImageView);

        mtxtDesc.setText(detail.getDescription());

        mAdapter.addData(detail.getListApp());
    }
}
