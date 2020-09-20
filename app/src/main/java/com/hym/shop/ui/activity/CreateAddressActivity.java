package com.hym.shop.ui.activity;


import android.content.Intent;
import android.net.MacAddress;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.hym.shop.R;
import com.hym.shop.bean.Address;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.User;
import com.hym.shop.bean.province.JsonBean;
import com.hym.shop.common.Constant;
import com.hym.shop.common.utils.ACache;
import com.hym.shop.common.utils.GetJsonDataUtil;
import com.hym.shop.dagger2.component.AppComponent;
import com.hym.shop.dagger2.component.DaggerAddressComponent;
import com.hym.shop.dagger2.module.AddressModule;
import com.hym.shop.presenter.AddressPresenter;
import com.hym.shop.presenter.contract.AddressContract;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.xuexiang.xui.widget.dialog.LoadingDialog;
import com.xuexiang.xui.widget.edittext.ClearEditText;

import org.json.JSONArray;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function4;

public class CreateAddressActivity extends ProgressActivity<AddressPresenter> implements AddressContract.AddressView {


    @BindView(R.id.consignee_name)
    ClearEditText mConsigneeName;
    @BindView(R.id.consignee_phone)
    ClearEditText mConsigneePhone;
    @BindView(R.id.consigneeAdr)
    TextView mConsigneeAdr;
    @BindView(R.id.consignee_address)
    ClearEditText mConsigneeAddress;
    @BindView(R.id.save)
    Button mSave;


    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区

    private long mUserId;
    private String mToken;

    private LoadingDialog mLoadingDialog;

    private Address mAddress;

    private int status = Constant.ADDRESS_ADD;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_create_address;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerAddressComponent.builder().appComponent(appComponent).addressModule(new AddressModule(this)).build().injectAdd(this);
    }

    @Override
    public void init() {

        initJsonData();
        setShowToolBarBack(true);
        getUser();
    }

    @Override
    public void initView() {
        setToolBarTitle("收貨地址");
        mLoadingDialog = new LoadingDialog(this);
        mAddress  = (Address) getIntent().getSerializableExtra(Constant.ADDRESS);
        if (mAddress != null){
            status = Constant.ADDRESS_EDIT;
            mConsigneeName.setText(mAddress.getConsignee());
            mConsigneePhone.setText(mAddress.getPhone());
            mConsigneeAdr.setText(mAddress.getAddr().split("-")[0]);
            mConsigneeAddress.setText(mAddress.getAddr().split("-")[1]);
        }
    }

    @Override
    public void initEvent() {
        mConsigneeAdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerView();
            }
        });
        saveAddress();
    }


    @Override
    public void showAddressList(List<Address> addressList) {

    }

    @Override
    public void addAddress(BaseBean baseBean) {
        if (baseBean.success()) {
            Intent intent = new Intent();
            intent.putExtra(Constant.ADDRESS, mAddress);
            // 设置返回码和返回携带的数据
            setResult(Constant.ADDRESS_ADD, intent);
            mAddress=null;
            Toast.makeText(this,"添加成功！",Toast.LENGTH_SHORT).show();
            finish();
        }else {
            if (mLoadingDialog.isShowing()){
                mLoadingDialog.dismiss();
            }
            Toast.makeText(this,"添加失败:"+baseBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateAddress(BaseBean baseBean) {
        if (baseBean.success()) {
            Intent intent = new Intent();
            mAddress.setConsignee(mConsigneeName.getText().toString());
            mAddress.setPhone(mConsigneePhone.getText().toString());
            mAddress.setAddr(mConsigneeAdr.getText().toString() + "-" +mConsigneeAddress.getText().toString());
            intent.putExtra(Constant.ADDRESS, mAddress);
            // 设置返回码和返回携带的数据
            setResult(Constant.ADDRESS_EDIT, intent);
            mAddress=null;
            Toast.makeText(this,"修改成功！",Toast.LENGTH_SHORT).show();
            finish();
        }else {
            if (mLoadingDialog.isShowing()){
                mLoadingDialog.dismiss();
            }
            Toast.makeText(this,"添加失败:"+baseBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void delAddress(BaseBean baseBean) {

    }


    private void showPickerView() {// 弹出选择器（省市区三级联动）
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);//收回输入法
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mConsigneeAdr.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));

            }
        })
                .setTitleText("城市选择")
                .setDividerColor(getResources().getColor(R.color.TextColor))
                .setTextColorCenter(getResources().getColor(R.color.theme_blue)) //设置选中项文字颜色
                .setContentTextSize(16)
                .setCancelColor(getResources().getColor(R.color.theme_grey))
                .setSubmitColor(getResources().getColor(R.color.theme_blue))
                .build();
        pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    private void initJsonData() {//解析数据 （省市区三级联动）
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    /**
     * 保存地址
     */
    private void saveAddress(){
        if (mAddress == null){
            mAddress = new Address();
            mAddress.setConsignee(mConsigneeName.getText().toString());
            mAddress.setPhone(mConsigneePhone.getText().toString());
            mAddress.setAddr(toUtf8(mConsigneeAdr.getText().toString() + "-" +mConsigneeAddress.getText().toString()));
            mAddress.setZip_code("000000");
            mAddress.setIsDefault(true);
        }
        RxView.clicks(mSave).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mLoadingDialog.show();
                if (status == Constant.ADDRESS_EDIT) {
                    mPresenter.updateAddress(mAddress.getId()
                            ,mConsigneeName.getText().toString()
                            ,mConsigneePhone.getText().toString()
                            ,mConsigneeAdr.getText().toString() + "-" +mConsigneeAddress.getText().toString()
                            ,mAddress.getZip_code()
                            ,mAddress.getIsDefault()
                            ,mToken
                            ,false);
                }else {
                    mPresenter.createAddress(mUserId,mAddress.getConsignee(), mAddress.getPhone(), mAddress.getAddr(),  mAddress.getZip_code() ,mToken,false);
                }

            }
        });
    }

    private void editTextListener(){
        InitialValueObservable<CharSequence> obConsigneeName = RxTextView.textChanges(mConsigneeName);
        InitialValueObservable<CharSequence> oConsigneePhone = RxTextView.textChanges(mConsigneePhone);
        InitialValueObservable<CharSequence> obConsigneeAdr = RxTextView.textChanges(mConsigneeAdr);
        InitialValueObservable<CharSequence> obConsigneeAddress = RxTextView.textChanges(mConsigneeAddress);
        InitialValueObservable.combineLatest(obConsigneeName, oConsigneePhone, obConsigneeAdr,obConsigneeAddress, new Function4<CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) throws Exception {
                return isPhoneValid(mConsigneePhone.getText().toString().trim());
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                mSave.setEnabled(aBoolean);
            }
        });
    }

    private boolean isPhoneValid(String phone){
        return phone.length() == 11;// && phone.startsWith("1");
    }

    private void getUser(){

        User user = (User) ACache.get(this).getAsObject(Constant.USER);

        mToken = ACache.get(this).getAsString(Constant.TOKEN);

        mUserId = user.getId();
    }


    @Override
    public void onBackPressed() {
        if (mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }else {
            super.onBackPressed();
        }

    }

    public static String toUtf8(String str) {
        String result = null;
        try {
            result = new String(str.getBytes("iso-8859-1"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
