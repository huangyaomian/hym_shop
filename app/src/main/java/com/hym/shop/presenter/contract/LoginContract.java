package com.hym.shop.presenter.contract;

import com.hym.shop.bean.BaseBean;
import com.hym.shop.bean.LoginBean;
import com.hym.shop.ui.BaseView;

import io.reactivex.Observable;


public interface LoginContract {



    interface ILoginModel{
        Observable<BaseBean<LoginBean>> login(String phone, String pwd);
    }

    interface LoginView extends BaseView {

        void checkPhoneError();
        void checkPhoneSuccess();

        void loginSuccess(LoginBean bean);

//        void loginError(String msg);  //errorhander里面处理了
    }


}
