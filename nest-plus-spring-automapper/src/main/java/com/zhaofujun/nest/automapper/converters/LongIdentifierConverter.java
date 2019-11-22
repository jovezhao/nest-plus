package com.zhaofujun.nest.automapper.converters;

import com.zhaofujun.automapper.converter.Converter;
import com.zhaofujun.nest.context.model.LongIdentifier;

public class LongIdentifierConverter extends Converter<LongIdentifier, Long> {
    @Override
    public Class<LongIdentifier> getSourceClass() {
        return LongIdentifier.class;
    }

    @Override
    public Class<Long> getTargetClass() {
        return Long.class;
    }

    @Override
    public Long toTarget(LongIdentifier longIdentifier) {
        return longIdentifier.getId();
    }

    @Override
    public LongIdentifier toSource(Long aLong) {
        return LongIdentifier.valueOf(aLong);
    }
}
