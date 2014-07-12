package com.liang.java8;

import java.util.Arrays;

/**
 * @author Briliang
 * @date 2014/7/12
 * Description(exercise lambda)
 */
public class lambda {
    public static void main(String[] args) {
        Arrays.asList("a","b","d").forEach((String e)->{
            System.out.println(e);
        });
        Arrays.asList("a","b","c","d").sort((e1,e2)->{
            int result=e1.compareTo(e2);
            return result;
        });

    }
}
