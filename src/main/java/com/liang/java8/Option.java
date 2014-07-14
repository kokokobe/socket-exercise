package com.liang.java8;

import java.util.Optional;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/7/14.
 * Description:(java8 新增option类来处理空值问题容器)
 */
public class Option {
    public static void main(String[] args) {
        Optional<String> firstName= Optional.of("BriLiang");
        System.out.println("First Name is set? "+firstName.isPresent());
        System.out.println("First Name: "+firstName.orElseGet(()->"[none]"));
        System.out.println(firstName.map(s->"Hey "+s+"!").orElse("Hey Stranger!"));
    }
}
