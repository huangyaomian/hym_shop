package com.hym.shop.bean;

import java.io.Serializable;
import java.util.List;

public class HomeCampaign implements Serializable {

    /**
     * cpOne : {"id":17,"title":"手机专享","imgUrl":"https://img.cniao5.com/555c6c90Ncb4fe515.jpg"}
     * cpTwo : {"id":15,"title":"闪购","imgUrl":"https://img.cniao5.com/560a26d2N78974496.jpg"}
     * cpThree : {"id":11,"title":"团购","imgUrl":"https://img.cniao5.com/560be0c3N9e77a22a.jpg"}
     * id : 1
     * title : 超值购
     * campaignOne : 17
     * campaignTwo : 15
     * campaignThree : 11
     */

    private Campaign cpOne;
    private Campaign cpTwo;
    private Campaign cpThree;
    private int id;
    private String title;
    private int campaignOne;
    private int campaignTwo;
    private int campaignThree;

    private List<Banner> banners;

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public Campaign getCpOne() {
        return cpOne;
    }

    public void setCpOne(Campaign cpOne) {
        this.cpOne = cpOne;
    }

    public Campaign getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(Campaign cpTwo) {
        this.cpTwo = cpTwo;
    }

    public Campaign getCpThree() {
        return cpThree;
    }

    public void setCpThree(Campaign cpThree) {
        this.cpThree = cpThree;
    }

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

    public int getCampaignOne() {
        return campaignOne;
    }

    public void setCampaignOne(int campaignOne) {
        this.campaignOne = campaignOne;
    }

    public int getCampaignTwo() {
        return campaignTwo;
    }

    public void setCampaignTwo(int campaignTwo) {
        this.campaignTwo = campaignTwo;
    }

    public int getCampaignThree() {
        return campaignThree;
    }

    public void setCampaignThree(int campaignThree) {
        this.campaignThree = campaignThree;
    }

}
