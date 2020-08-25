package com.zhaofujun.nest.demo.domain;

import com.zhaofujun.nest.context.model.BaseValueObject;

public class Address extends BaseValueObject {
    private String name;
    private Point point;

    public Address(String name, Point point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public Point getPoint() {
        return point;
    }
}
