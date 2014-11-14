/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-14 下午6:22
 * Project Name: socket-exercise
 * File Name: HttpUrlResolver.java
 */

package com.liang.annotation;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/14.
 * Description:(模拟spring http 请求转换为对应路径方法的参数)
 */
public class HttpUrlResolver {
    private final List<Method> annotattionMethods;

    public HttpUrlResolver(List<Method> methods) {
        annotattionMethods = methods;
    }

    protected Method resolveHrlToAnnotateMethod(String url){
        if(annotattionMethods!=null&&annotattionMethods.size()>0){
            for(Method method:annotattionMethods){

            }
        }
        return null;
    }
}
