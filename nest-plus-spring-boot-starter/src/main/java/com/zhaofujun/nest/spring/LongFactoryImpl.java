package com.zhaofujun.nest.spring;

import com.zhaofujun.nest.context.model.LongIdentifier;
import com.zhaofujun.nest.utils.identifier.LongGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LongFactoryImpl implements LongFactory {

    private ApplicationContext applicationContext;

    public LongFactoryImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public Long createLong(String generatorCode,String type) {
        LongGenerator longGenerator = getLongGenerator(generatorCode);
        Long value = longGenerator.nextValue(type);
        return value;
    }


    public LongIdentifier create(String generatorCode,String type) {

        return LongIdentifier.valueOf(createLong(type, generatorCode));
    }

    private LongGenerator getLongGenerator(String generatorCode) {
        LongGenerator identifierGenerator = applicationContext.getBeansOfType(LongGenerator.class)
                .values()
                .stream()
                .filter(p -> p.getName().equals(generatorCode))
                .findFirst()
                .get();
        if (identifierGenerator == null) {
            return getLongGenerator("snowflakeLongIdentifierGenerator");
        }
        return identifierGenerator;
    }
}
