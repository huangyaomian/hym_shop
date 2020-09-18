package com.hym.shop.bean;

import java.io.Serializable;

public class Address implements Serializable , Comparable<Address>{

    private Long id;
    private Long userId;
    private String consignee;
    private String phone;
    private String addr;
    private String zip_code;
    private Boolean isDefault;

    public Address() {

    }

    public Address(String consignee, String phone, String addr, String zipCode) {
        this.consignee = consignee;
        this.phone = phone;
        this.addr = addr;
        this.zip_code = zipCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    //地址排序
    @Override
    public int compareTo(Address another) {

        if (another.getIsDefault() != null && this.getIsDefault() != null)
            return another.getIsDefault().compareTo(this.getIsDefault());

        return -1;
    }
}
