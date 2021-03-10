package com.zhaofujun.nest.spring;


import com.zhaofujun.nest.context.model.LongIdentifier;

public interface LongFactory {
    public static final String snowflakeCode="snowflakeLongGenerator";
    public static final String localCode="localLongGenerator";
    Long createLong(String generatorName,String type);
    LongIdentifier create(String generatorName,String type);
}
