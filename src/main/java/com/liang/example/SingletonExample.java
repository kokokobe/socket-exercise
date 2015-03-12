/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 15-3-12 上午10:19
 * Project Name: socket-exercise
 * File Name: SinletonExample.java
 */

package com.liang.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2015/3/12.
 * Description:(线程安全的单例模式,避免了类装载器不同的加载实例不同的问题,lazy loading
 * ，不能避免序列化，反序列化后生成的新实例)
 */
public class SingletonExample {
    private static class SingletonHolder{
        private static final SingletonExample instance =  new SingletonExample();
    }
    private SingletonExample (){

    }
    public static final SingletonExample getSingletonExample(){
        return SingletonHolder.instance;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        SingletonExample2 singletonExample2 = SingletonExample2.getSingletonExample2();
        class GetInstance implements Runnable{
            @Override
            public void run() {
                if(!SingletonExample2.getSingletonExample2().equals(singletonExample2)){
                    System.out.println("not equal");
                }
            }
        }
        for(;;){
            executorService.execute(new GetInstance());
        }
    }
}
