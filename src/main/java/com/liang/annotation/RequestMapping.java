/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-14 下午4:11
 * Project Name: socket-exercise
 * File Name: HttpGet.java
 */

package com.liang.annotation;

import java.lang.annotation.*;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/14.
 * Description:()
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String[] value() default {};
    RequestMethod[] method() default {};
    String[] params() default {};
    String[] headers() default {};
    String[] consumes() default {};
    String[] produces() default {};

    /**
     * 处理解析注释
     */
    final class RequestMappingBuilder{

    }
}
