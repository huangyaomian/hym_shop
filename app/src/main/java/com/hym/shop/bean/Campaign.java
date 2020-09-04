package com.hym.shop.bean;

import java.io.Serializable;

public class Campaign implements Serializable {


    /**
     * id : 17
     * title : 手机专享
     * imgUrl : https://img.cniao5.com/555c6c90Ncb4fe515.jpg
     */

    private int id;
    private String title;
    private String imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
