package com.hym.shop.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.StatusBarManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.hym.shop.R;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.font.Cniao5Font;
import com.hym.shop.common.imageloader.GlideCircleTransform;
import com.hym.shop.common.imageloader.ImageLoader;
import com.hym.shop.common.rx.RxBus;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.common.utils.StatusBarUtil;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerHotWaresComponent;
import com.hym.shop.dagger2.component.DaggerMineComponent;
import com.hym.shop.dagger2.module.HotWaresModule;
import com.hym.shop.dagger2.module.MineModule;
import com.hym.shop.presenter.HotWaresPresenter;
import com.hym.shop.presenter.MinePresenter;
import com.hym.shop.presenter.contract.HotWaresContract;
import com.hym.shop.presenter.contract.MineContract;
import com.hym.shop.ui.activity.LoginActivity;
import com.hym.shop.ui.activity.MainActivity;
import com.hym.shop.ui.adapter.HotWaresAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration4;
import com.mikepenz.iconics.IconicsDrawable;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MineFragment extends ProgressFragment<MinePresenter> implements MineContract.MineView {


    @BindView(R.id.user_info)
    LinearLayout mLinearLayout;
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

    private User mUser;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).mineModule(new MineModule(this)).build().inject(this);
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
        RxBus.getDefault().toObservable(User.class).subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) {
                mPresenter.getUser(false);
            }
        });
        mPresenter.getUser(true);
    }

    @Override
    protected void initEvent() {
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUser == null) {
                    Intent intent = new Intent(getContext(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }




    private void logout() {
        mUser =null;
        ACache aCache = ACache.get(getContext());
        aCache.put(Constant.TOKEN, "");
        aCache.put(Constant.USER, "");
        mRadiusImageView.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_no_log));
        mUsername.setText(R.string.no_login);
        Toast.makeText(getContext(), "退出登錄成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMine(User user) {
        if (user ==  null){

        }else {
            mLogoutBtn.setVisibility(View.VISIBLE);
            mUser =user;
            mUsername.setText(user.getUsername());
            ImageLoader.load("http:" + user.getLogo_url(),mRadiusImageView);
        }


    }



}
