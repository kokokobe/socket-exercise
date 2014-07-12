package com.liang.java8.repeatingannotation;

import java.lang.annotation.*;

/**
 * @author Briliang
 * @date 2014/7/12
 * Description(重复性注解示例)
 */
public class RepatingAnnotations {
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Filters{
        Filter[] value();
    }
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Filters.class)
    public @interface Filter{
        String value();
    };
    @Filter("filter1")
    @Filter("filter2")
    public interface Filterable{

    }

    public static void main(String[] args) {
        for(Filter fileter:Filterable.class.getAnnotationsByType(Filter.class)){
            System.out.println(fileter.value());
        }
    }

}
