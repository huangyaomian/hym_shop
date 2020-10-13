package com.hym.shop.common.imageloader;

import android.graphics.Bitmap;

/**
 * bitmap loading监听
 * @author Mika.
 * @created 2020/10/12 19:00.
 */
public interface BitmapLoadingListener {

    void onSuccess(Bitmap b);

    void onError();
}
