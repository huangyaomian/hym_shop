package com.hym.shop.presenter;

import com.hym.shop.bean.Address;
import com.hym.shop.bean.BaseBean;
import com.hym.shop.common.rx.subscriber.ProgressDisposableObserver;
import com.hym.shop.presenter.contract.AddressContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AddressPresenter extends BasePresenter<AddressContract.IAddressModel, AddressContract.AddressView> {


    @Inject
    public AddressPresenter(AddressContract.IAddressModel iAddressModel, AddressContract.AddressView addressView) {
        super(iAddressModel, addressView);
    }



    public void showAddressList(Long userId, String token, boolean isShowProgress){

        ProgressDisposableObserver<List<Address>> progressDisposableObserver = new ProgressDisposableObserver<List<Address>>(mContext, mView) {
            @Override
            public void onNext(List<Address> addressList) {
                mView.showAddressList(addressList);
            }

            @Override
            protected boolean isShowProgress() {
                return isShowProgress;
            }
        };

        mModel.getAddressList( userId, token)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(progressDisposableObserver);

        addDisposable(progressDisposableObserver);


//        mModel.getAddressList( userId, token)
//                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
//                .subscribe(new ProgressDisposableObserver<List<Address>>(mContext,mView) {
//                    @Override
//                    public void onNext(List<Address> addressList) {
//                        mView.showAddressList(addressList);
//                    }
//                    @Override
//                    protected boolean isShowProgress() {
//                        return isShowProgress;
//                    }
//                });

    }

    public void createAddress(Long user_id, String consignee, String phone, String addr, String zip_code, String token, boolean isShowProgress){
        mModel.createAddress( user_id,  consignee, phone, addr,  zip_code,token)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<BaseBean>(mContext,mView) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.addAddress(baseBean);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }
                });
    }

    public void updateAddress(Long id, String consignee, String phone, String addr, String zip_code, boolean is_default, String token, boolean isShowProgress){
        mModel.updateAddress(id, consignee,phone,addr,zip_code,is_default,token)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<BaseBean>(mContext,mView) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.updateAddress(baseBean);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }
                });
    }

    public void delAddress(Long id, String token, boolean isShowProgress){
        mModel.delAddress(id,token)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new ProgressDisposableObserver<BaseBean>(mContext,mView) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.delAddress(baseBean);
                    }
                    @Override
                    protected boolean isShowProgress() {
                        return isShowProgress;
                    }
                });
    }








}
