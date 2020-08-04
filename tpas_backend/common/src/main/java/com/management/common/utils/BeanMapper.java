package com.management.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

import java.util.List;

/**
 * @author Pietra
 * @version 1.0
 * @classname BeanMapper
 * @description BeanOfClasssA<->BeanOfClassB复制，要求字段属性名完全相同，不同则为null
 * @date 2020/8/2
 **/
public class BeanMapper {

    private static MapperFacade mapper;

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapper = mapperFactory.getMapperFacade();
    }


    /**
    * @Description 简单的复制出新类型对象.通过getClass() 获得源Class
    * @param source
    * @param destinationClass
    * @Return D
    * @Author Pietra
    * @Date 2020/8/2
    **/
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    /**
    * @Description 极致性能的复制出新类型对象.预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
    * @param source
    * @param sourceType
    * @param destinationType
    * @Return D
    * @Author peihua.wu
    * @Date 2020/8/2
    **/
    public static <S, D> D map(S source, Type<S> sourceType, Type<D> destinationType) {
        return mapper.map(source, sourceType, destinationType);
    }

    /**
    * @Description 简单的复制出新对象列表到ArrayList,mapper.mapAsList(Iterable<S>,Class<D>)接口调用反射机制，较慢,不采用
    * @param sourceList
    * @param sourceClass
    * @param destinationClass
    * @Return java.util.List<D>
    * @Author peihua.wu
    * @Date 2020/8/2
    **/
    public static <S, D> List<D> mapAsList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass) {
        return mapper.mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destinationClass));
    }

    /**
    * @Description 极致性能的复制出新类型对象到ArrayList.预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
    * @param sourceList
    * @param sourceType
    * @param destinationType
    * @Return java.util.List<D>
    * @Author peihua.wu
    * @Date 2020/8/2
    **/
    public static <S, D> List<D> mapAsList(Iterable<S> sourceList, Type<S> sourceType, Type<D> destinationType) {
        return mapper.mapAsList(sourceList, sourceType, destinationType);
    }

    /**
    * @Description 简单复制出新对象列表到数组,通过getComponentType() 获得源Class
    * @param destination
    * @param source
    * @param destinationClass
    * @Return D[]
    * @Author peihua.wu
    * @Date 2020/8/2
    **/
    public static <S, D> D[] mapAsArray(final D[] destination, final S[] source, final Class<D> destinationClass) {
        return mapper.mapAsArray(destination, source, destinationClass);
    }

    /**
    * @Description 极致性能的复制出新类型对象到数组.预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
    * @param destination
    * @param source
    * @param sourceType
    * @param destinationType
    * @Return D[]
    * @Author peihua.wu
    * @Date 2020/8/2
    **/
    public static <S, D> D[] mapAsArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
        return mapper.mapAsArray(destination, source, sourceType, destinationType);
    }

}
