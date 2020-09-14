package com.hym.shop.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hym.shop.R;
import com.hym.shop.bean.FragmentInfo;
import com.hym.shop.bean.HotWares;
import com.hym.shop.common.db.DBManager;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.ui.adapter.MyViewPagerAdapter2;
import com.hym.shop.ui.fragment.SortWaresFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WaresDetailActivity extends ProgressActivity {




    @BindView(R.id.web_view)
    WebView mWebView;

    private HotWares.WaresBean mWaresBean;

    private WebAppInterface mAppInterface;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.template_webview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }


    @Override
    public void init() {
        setShowToolBarBack(true);
        mWaresBean = (HotWares.WaresBean) getIntent().getSerializableExtra("wares");
        if(mWaresBean ==null)
            this.finish();
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        setToolBarTitle("商品詳情");

    }



    @Override
    public void initView() {
        initWebView();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(){

        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

        mWebView.loadUrl("http://112.124.22.238:8081/course_api/wares/detail.html");

        mAppInterface = new WebAppInterface(this);
        mWebView.addJavascriptInterface(mAppInterface,"appInterface");
        mWebView.setWebViewClient(new WC());


    }




    @Override
    public void initEvent() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar, menu);
        menu.findItem(R.id.upper_right_corner).setIcon(new IconicsDrawable(this, Ionicons.Icon.ion_share).color(getResources().getColor(R.color.TextColor)).actionBar());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("hymmmm", "onOptionsItemSelected: " +item.getItemId());
        switch (item.getItemId()) {
            case R.id.upper_right_corner:
                share();
                break;
        }
        return true;
    }

    private void share(){
        OnekeyShare oks = new OnekeyShare();
// title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(getString(R.string.share));
// titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// setImageUrl是网络图片的url
        oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
// url在微信、Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
// 启动分享GUI
        oks.show(MobSDK.getContext());
    }

    class  WC extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mAppInterface.showDetail();
        }
    }


    class WebAppInterface{


        private Context mContext;
        public WebAppInterface(Context context){
            mContext = context;
        }

        @JavascriptInterface
        public  void showDetail(){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:showDetail("+mWaresBean.getId()+")");
                }
            });
        }


        @JavascriptInterface
        public void buy(long id){

            DBManager.insertWares(mWaresBean);

            Toast.makeText(mContext,"添加成功",Toast.LENGTH_SHORT).show();

        }

        @JavascriptInterface
        public void addFavorites(long id){


        }

    }



}
