package com.zhaofujun.nest.demo.domain;

import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.BaseEntity;

public class Order extends BaseEntity<StringIdentifier> {

    private WebUser seller;

    public void setSeller(WebUser seller) {
        this.seller = seller;
    }

    public WebUser getSeller() {
        return seller;
    }
}
