/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-14 下午6:22
 * Project Name: socket-exercise
 * File Name: HttpUrlResolver.java
 */

package com.liang.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/14.
 * Description:(模拟spring http 请求转换为对应路径方法的参数)
 */
public class HttpUrlResolver {
    private final Method[] annotattionMethods;

    public HttpUrlResolver(Method[] methods) {
        annotattionMethods = methods;
    }

    protected Method resolveHrlToAnnotateMethod(String url){
        if(annotattionMethods!=null&&annotattionMethods.length>0){
            for(Method method:annotattionMethods){
                if(method.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping annotation= method.getAnnotation(RequestMapping.class);
                    String[] paths=annotation.value();
                    for(String path:paths){
                        if(url.indexOf(path)!=-1){
                            return method;
                        }
                    }

                }
            }
            System.out.printf("can't find the path: "+url);
        }
        return null;
    }
}
