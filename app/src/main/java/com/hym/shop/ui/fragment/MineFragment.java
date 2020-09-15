package com.hym.shop.ui.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.Constant;
import com.hym.shop.common.font.Cniao5Font;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.presenter.HotWaresPresenter;
import com.hym.shop.presenter.contract.HotWaresContract;
import com.hym.shop.ui.activity.LoginActivity;
import com.hym.shop.ui.activity.MainActivity;
import com.hym.shop.ui.adapter.HotWaresAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration4;
import com.mikepenz.iconics.IconicsDrawable;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import butterknife.BindView;

public class MineFragment extends ProgressFragment<HotWaresPresenter> implements HotWaresContract.HotWaresView {


    @BindView(R.id.radius_image_view)
    RadiusImageView mRadiusImageView;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.my_order)
    TextView mMyOrder;
    @BindView(R.id.my_favorites)
    TextView mMyFavorites;
    @BindView(R.id.my_addr)
    TextView mMyAddr;
    @BindView(R.id.logout_btn)
    Button mLogoutBtn;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
//        DaggerHotWaresComponent.builder().appComponent(appComponent).hotWaresModule(new HotWaresModule(this)).build().inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void init() {

        showToolBar();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItemDecoration4 dividerDecoration = new SpaceItemDecoration4(UIUtils.dp2px(8));

    }

    @Override
    protected void initEvent() {

    }


    @Override
    public void showHotWares(HotWares hotWares) {


    }


    private void logout() {
        ACache aCache = ACache.get(getContext());
        aCache.put(Constant.TOKEN, "");
        aCache.put(Constant.USER, "");
        mRadiusImageView.setImageDrawable(new IconicsDrawable(getContext(), Cniao5Font.Icon.cniao_head).colorRes(R.color.theme_while));
        mUsername.setText(R.string.no_login);
        Toast.makeText(getContext(), "退出登錄", Toast.LENGTH_LONG).show();
        mRadiusImageView.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_no_log));
    }
}
