package com.hym.shop.common.utils;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;



/**
  * 權限工具類
  * @author Mika.
  * @created 2020/10/12 19:07.
  */
public class PermissionUtil {



    public static Observable<Boolean> requestPermission(Context activity, String permission){


        RxPermissions rxPermissions = new RxPermissions((FragmentActivity) activity);

        return rxPermissions.request(permission);
    }

    public static Observable<Boolean> requestPermissions(Context activity, String... permissions){


        RxPermissions rxPermissions = new RxPermissions((FragmentActivity) activity);

        return rxPermissions.request(permissions);
    }

}
