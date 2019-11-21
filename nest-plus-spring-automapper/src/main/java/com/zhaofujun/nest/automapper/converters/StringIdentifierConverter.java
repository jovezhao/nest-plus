package com.zhaofujun.nest.automapper.converters;

import com.zhaofujun.automapper.converter.Converter;
import com.zhaofujun.nest.context.model.StringIdentifier;

public class StringIdentifierConverter extends Converter<StringIdentifier, String> {
    @Override
    public Class<StringIdentifier> getSourceClass() {
        return StringIdentifier.class;
    }

    @Override
    public Class<String> getTargetClass() {
        return String.class;
    }

    @Override
    public String toTarget(StringIdentifier stringIdentifier) {
        return stringIdentifier.toValue();
    }

    @Override
    public StringIdentifier toSource(String s) {
        return StringIdentifier.valueOf(s);
    }
}
