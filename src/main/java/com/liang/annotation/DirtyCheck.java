/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-14 下午3:11
 * Project Name: socket-exercise
 * File Name: DirtyCheck.java
 */
package com.liang.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/14.
 * Description:(compare two string is equal?)
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DirtyCheck {
    String newValue() default "";
    String oldValue() default "";
    final public class DirtyChecker{
        public boolean process(Object instance){
            Class<?> clz=instance.getClass();
            for(Method method:clz.getDeclaredMethods()){
                if(method.isAnnotationPresent(DirtyCheck.class)){
                    DirtyCheck annotation= method.getAnnotation(DirtyCheck.class);
                    String newVal=annotation.newValue();
                    String oldVal=annotation.oldValue();
                    return newVal.equals(oldVal);
                }
            }
            return false;
        }

    }
}
