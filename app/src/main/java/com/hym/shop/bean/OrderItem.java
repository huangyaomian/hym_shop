package com.hym.shop.bean;

import java.io.Serializable;

public class OrderItem implements Serializable {
    public Long id;
    private Long orderId;
    private Long ware_id;
    private HotWares.WaresBean wares;

    public HotWares.WaresBean getWares() {
        return wares;
    }

    public void setWares(HotWares.WaresBean wares) {
        this.wares = wares;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getWare_id() {
        return ware_id;
    }

    public void setWare_id(Long ware_id) {
        this.ware_id = ware_id;
    }
}
