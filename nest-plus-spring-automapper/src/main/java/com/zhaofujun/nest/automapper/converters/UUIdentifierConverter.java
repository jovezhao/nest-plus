package com.zhaofujun.nest.automapper.converters;

import com.zhaofujun.automapper.converter.Converter;
import com.zhaofujun.nest.context.model.UUIdentifier;

public class UUIdentifierConverter extends Converter<UUIdentifier, String> {
    @Override
    public Class<UUIdentifier> getSourceClass() {
        return UUIdentifier.class;
    }

    @Override
    public Class<String> getTargetClass() {
        return String.class;
    }

    @Override
    public String toTarget(UUIdentifier uuIdentifier) {
        return uuIdentifier.toValue();
    }

    @Override
    public UUIdentifier toSource(String s) {
        return UUIdentifier.valueOf(s);
    }
}
