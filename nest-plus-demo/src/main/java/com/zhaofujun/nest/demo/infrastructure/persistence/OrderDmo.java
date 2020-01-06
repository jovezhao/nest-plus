package com.zhaofujun.nest.demo.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("orders")
public class OrderDmo {


    private String id;
    private String sellerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
