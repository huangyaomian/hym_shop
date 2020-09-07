package com.hym.shop.app;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.multidex.MultiDex;

import com.hym.shop.common.db.DBManager;
import com.hym.shop.dagger2.component.DaggerAppComponent;
import com.hym.shop.dagger2.module.AppModule;
import com.xuexiang.xui.XUI;


public class MyApplication extends Application {


    private DaggerAppComponent mAppComponent;
    private View mView;



    public static MyApplication get(Context context){
        return (MyApplication) context.getApplicationContext();
    }

    public DaggerAppComponent getAppComponent(){
        return mAppComponent;
    }



    @Override
    public void onCreate() {
        super.onCreate();
//        DBManager.initDB(this);
        mAppComponent = (DaggerAppComponent) DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        XUI.init(this); //初始化UI框架
        //XUI.debug(true);  //开启UI框架调试日志

        MultiDex.install(this);


    }

    public View getView() {
        return mView;
    }

    public void setView(View mView) {
        this.mView = mView;
    }
}
