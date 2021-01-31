package com.gree.executor;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author yangLongFei 2021-01-29-20:36
 */
@Component
public class MapperFactoryFactoryBean implements FactoryBean<MapperFactory> {
    @Override
    public MapperFactory getObject() throws Exception {
        return new DefaultMapperFactory.Builder().build();
    }

    @Override
    public Class<?> getObjectType() {
        return MapperFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
