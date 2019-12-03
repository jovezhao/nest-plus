package com.zhaofujun.nest.demo.domain;

import com.zhaofujun.nest.CustomException;

public class EntityVerifyException extends CustomException {
    public EntityVerifyException(String message, Object... arguments) {
        super(5000, message, arguments);
    }
}
