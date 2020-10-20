package com.hym.shop.ui;

public interface BaseView {
    void showLoading();
    void showError(String msg,int errorCode);
    void dismissLoading();
}
