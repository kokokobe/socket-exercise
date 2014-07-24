package com.liang.java8.parallel;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Briliang
 * @date 2014/7/22
 * Description()
 */
public class ParallelArrays {
    public static void main(String[] args) {
        Long[] arrayOfLong=new Long[20000];
        Arrays.parallelSetAll(arrayOfLong,index-> ThreadLocalRandom.current().nextLong(100000
        ));
        Arrays.stream(arrayOfLong).limit(10).forEach(i-> System.out.print(i + " "));
        System.out.println();
        Arrays.parallelSort(arrayOfLong);
        Arrays.stream(arrayOfLong).limit(10).forEach(i-> System.out.print(i+" "));
    }
}
