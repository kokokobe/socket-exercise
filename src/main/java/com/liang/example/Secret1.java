/*
 * Copyright (c) BriLiang All rights reserved.
 * File create : 15-3-13 下午2:15
 * Project Name: socket-exercise
 * File Name: Secrect1.java
 */

package com.liang.example;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2015/3/13.
 * Description:(+操作符的作用)
 */
public class Secret1 {
    public static void main(String[] args) {
        System.out.println("H" + "a");
        System.out.println('H' + 'a');
         /*有转换类型的作用*/
        System.out.println("" + 'H' + 'a');
        System.out.println("2+2= " + 2 + 2);
        System.out.printf("%c%c", 'H', 'a');
        System.out.println();
        System.out.println("a\u0022.length() +\u0022b".length());
        System.out.println("a\".length()+\"b".length());


    }

}
