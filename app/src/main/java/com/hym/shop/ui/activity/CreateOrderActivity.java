package com.hym.shop.ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hym.shop.R;
import com.hym.shop.app.MyApplication;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.Charge;
import com.hym.shop.bean.HotWares;
import com.hym.shop.bean.OrderRespMsg;
import com.hym.shop.bean.User;
import com.hym.shop.common.Constant;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.common.utils.UIUtils;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerCategoryComponent;
import com.hym.shop.dagger2.component.DaggerCreateOrderComponent;
import com.hym.shop.dagger2.module.CreateOrderModule;
import com.hym.shop.presenter.AppDetailPresenter;
import com.hym.shop.presenter.CreateOrderPresenter;
import com.hym.shop.presenter.contract.AppInfoContract;
import com.hym.shop.presenter.contract.CreateOrderContract;
import com.hym.shop.ui.adapter.CreateOrderWaresAdapter;
import com.hym.shop.ui.widget.SpaceItemDecoration4;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.pingplusplus.android.PaymentActivity;
import com.pingplusplus.android.Pingpp;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateOrderActivity extends ProgressActivity<CreateOrderPresenter> implements CreateOrderContract.CreateOrderView, View.OnClickListener {


    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    private static final String CHANNEL_BFB = "bfb_wap";


    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_addr)
    TextView mTvAddr;
    @BindView(R.id.img_add)
    ImageView mImgAdd;
    @BindView(R.id.rl_addr)
    RelativeLayout mRlAddr;
    @BindView(R.id.tv_order)
    TextView mTvOrder;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.ll_items)
    LinearLayout mLlItems;
    @BindView(R.id.img_alipay)
    ImageView mImgAlipay;
    @BindView(R.id.rb_alipay)
    RadioButton mRbAlipay;
    @BindView(R.id.rl_alipay)
    RelativeLayout mRlAlipay;
    @BindView(R.id.img_wechat)
    ImageView mImgWechat;
    @BindView(R.id.rb_wechat)
    RadioButton mRbWechat;
    @BindView(R.id.rl_wechat)
    RelativeLayout mRlWechat;
    @BindView(R.id.img_bd)
    ImageView mImgBd;
    @BindView(R.id.rb_bd)
    RadioButton mRbBd;
    @BindView(R.id.rl_bd)
    RelativeLayout mRlBd;
    @BindView(R.id.ll_pay)
    LinearLayout mLlPay;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.btn_createOrder)
    Button mBtnCreateOrder;

    private CreateOrderWaresAdapter mCreateOrderWaresAdapter;

    private List<WareItem> wareItemList = new ArrayList<>();

    private double totalPrice = 0.0;
    private String payChannel = CHANNEL_ALIPAY;//默认途径为支付宝

    private String orderNum;
    private int SIGN;

    private HashMap<String, RadioButton> channels = new HashMap<>(3);

    private String mToken;

    private int mPayStatus;


    @Inject
    public Gson mGson;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_create_order;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCreateOrderComponent.builder().appComponent(appComponent).createOrderModule(new CreateOrderModule(this)).build().inject(this);
    }


    @Override
    public void init() {
        setShowToolBarBack(true);
        mPresenter.getWares(true);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        setToolBarTitle("提交訂單");
    }


    @Override
    public void initView() {
        mCreateOrderWaresAdapter = new CreateOrderWaresAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SpaceItemDecoration4 dividerDecoration = new SpaceItemDecoration4(UIUtils.dp2px(4));
        mRecycleView.addItemDecoration(dividerDecoration);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mCreateOrderWaresAdapter);

        initPayChannels();
    }


    @Override
    public void initEvent() {
        mBtnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOrderByCart(wareItemList);
            }
        });
    }



    @Override
    public void showWares(List<HotWares.WaresBean> waresBeanList) {
        mCreateOrderWaresAdapter.addData(waresBeanList);
        setAllCheckAndTotalPrice(waresBeanList);
        for (int i = 0; i < waresBeanList.size(); i++) {
           int amount = (int) (waresBeanList.get(i).getCount() * waresBeanList.get(i).getPrice());
            WareItem wareItem = new WareItem((long) waresBeanList.get(i).getId(),amount);
            wareItemList.add(wareItem);
        }
    }

    @Override
    public void showSubmitOrderResult(BaseBean<OrderRespMsg> orderRespMsgBaseBean) {
        orderNum = orderRespMsgBaseBean.getData().getOrderNum();
        Charge charge = orderRespMsgBaseBean.getData().getCharge();
        openPaymentActivity(mGson.toJson(charge));
    }

    @Override
    public void showCompleteOrderResult(BaseBean baseBean) {
        if (baseBean.success()) {
            toPayResultActivity(mPayStatus);
        }
    }

    private void setAllCheckAndTotalPrice(List<HotWares.WaresBean> waresBeanList){
        for (int i = 0; i < waresBeanList.size(); i++) {
            totalPrice = totalPrice + (waresBeanList.get(i).getPrice() * waresBeanList.get(i).getCount());

        }
        mTvTotal.setText("實付：￥" +totalPrice);
    }

    /**
     * 提交购物车订单
     *
     * @param items 商品集合
     */
    private void submitOrderByCart(List<WareItem> items) {

        String item_json = mGson.toJson(items);

        User user = (User) ACache.get(this).getAsObject(Constant.USER);

        mToken = ACache.get(this).getAsString(Constant.TOKEN);

        String userId = user.getId() + "";

        mPresenter.submitOrder(Long.parseLong(userId),item_json,(int)totalPrice,1,payChannel,mToken);
    }

    private void initPayChannels() {
        //保存RadioButton
        channels.put(CHANNEL_ALIPAY, mRbAlipay);
        channels.put(CHANNEL_WECHAT, mRbWechat);
        channels.put(CHANNEL_BFB, mRbBd);

        mRlAlipay.setOnClickListener(this);
        mRlWechat.setOnClickListener(this);
        mRlBd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        selectPayChannel(view.getTag().toString());
    }

    /**
     * 选择支付渠道以及RadioButton互斥功能
     *
     * @param payChannel
     */
    private void selectPayChannel(String payChannel) {

        for (Map.Entry<String, RadioButton> entry : channels.entrySet()) {

            this.payChannel = payChannel;

            System.out.println("payChannel=" + payChannel);

            //获取的RadioButton
            RadioButton rb = entry.getValue();

            //如果当前RadioButton被点击
            if (entry.getKey().equals(payChannel)) {

                //判断是否被选中
                boolean isChecked = rb.isChecked();

                //设置为互斥操作
                rb.setChecked(!isChecked);

            } else {
                //其他的都改为未选中
                rb.setChecked(false);
            }


        }
    }

    /**
     * 显示模拟支付页面
     *
     * @param charge
     */
    private void openPaymentActivity(String charge) {
        Pingpp.enableMiddlePage(true, 1.0);
        Pingpp.createPayment(CreateOrderActivity.this, charge);

    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                payResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 支付页面返回处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void payResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                if (result.equals("success")) {
                    changeOrderStatus(Constant.SUCCESS);
                    mPayStatus = Constant.SUCCESS;
                } else if (result.equals("fail")) {
                    changeOrderStatus(Constant.FAIL);
                    mPayStatus = Constant.FAIL;
                } else if (result.equals("cancel")) {
                    changeOrderStatus(Constant.CANCEL);
                    mPayStatus = Constant.CANCEL;
                } else {
                    changeOrderStatus(Constant.INVALID);
                    mPayStatus = Constant.INVALID;
                }

            }
        }
    }

    /**
     * 修改订单状态
     * 请求跳转到支付结果页面
     * 失败直接返回请求失败状态
     *
     * @param status
     */
    private void changeOrderStatus(final int status) {
        mPresenter.completeOrder(orderNum,status+"",mToken);
    }

    private void toPayResultActivity(int status) {
        Intent intent = new Intent(CreateOrderActivity.this,PayResultActivity.class);
        intent.putExtra(Constant.PAY_STATUS,status);
        startActivity(intent);
        finish();
    }


    /**
     * 商品id和价格显示适配器
     */
    class WareItem {
        private Long ware_id;
        private int amount;

        public WareItem(Long ware_id, int amount) {
            this.ware_id = ware_id;
            this.amount = amount;
        }

        public Long getWare_id() {
            return ware_id;
        }

        public void setWare_id(Long ware_id) {
            this.ware_id = ware_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
