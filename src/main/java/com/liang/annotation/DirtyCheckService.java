/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-14 下午4:06
 * Project Name: socket-exercise
 * File Name: DirtyCheckService.java
 */

package com.liang.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/11/14.
 * Description:()
 */
public class DirtyCheckService {
    @DirtyCheck(newValue = "new1",oldValue = "new1")
    protected void checkIsEqual() {
        System.out.printf("checking is begin");
    }
    @RequestMapping(value = "/user",method = RequestMethod.GET,produces = "application/json")
    protected String getStartupMethod(String defaultValue){
        System.out.println("invoke data : "+defaultValue);
        return "data:[] "+defaultValue;
    }
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        DirtyCheck.DirtyChecker dirtyChecker=new DirtyCheck.DirtyChecker();
        System.out.println("process the dirty checker return: "+dirtyChecker.process(new DirtyCheckService()));
        /*初始化时加载所有的 class method seem to spring component scan*/
        Method method=new HttpUrlResolver(DirtyCheckService.class.getDeclaredMethods()).resolveHrlToAnnotateMethod("/user");
        method.invoke(method.getDeclaringClass().newInstance(),"BriLiang resolver");
    }
}
