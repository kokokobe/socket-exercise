/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 15-3-12 上午10:27
 * Project Name: socket-exercise
 * File Name: SingletonExample2.java
 */

package com.liang.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2015/3/12.
 * Description:(双重校验锁机制，线程安全 ,不能避免序列化，反序列化后生成的新实例
 * ，不需要序列化的话是可以使用)
 */
public class SingletonExample2 {
    private volatile static SingletonExample2 singletonExample2;
    private SingletonExample2(){

    }
    public static final SingletonExample2 getSingletonExample2() {
        if (singletonExample2 == null) {
            synchronized (SingletonExample2.class) {
                if (singletonExample2 == null) {
                    singletonExample2 =new SingletonExample2();
                    return singletonExample2;
                }
            }
        }
        return singletonExample2;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        class GetInstance implements Runnable{
            @Override
            public void run() {
                System.out.println(SingletonExample.getSingletonExample());
            }
        }
        for(;;){
            executorService.execute(new GetInstance());
        }
    }
}
