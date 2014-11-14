/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 14-11-14 下午4:06
 * Project Name: socket-exercise
 * File Name: DirtyCheckService.java
 */

package com.liang.annotation;

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
        return "data:[] "+defaultValue;
    }
    public static void main(String[] args) {
        DirtyCheck.DirtyChecker dirtyChecker=new DirtyCheck.DirtyChecker();
        System.out.printf("process the dirty checker return: "+dirtyChecker.process(new DirtyCheckService()));
    }
}
