package com.zhaofujun.nest.demo.domain;

import com.zhaofujun.nest.context.model.BaseValueObject;

public class Point extends BaseValueObject {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
