package com.liang.java8;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/7/14.
 * Description:(java 8 类型推测机制)
 */
public class Value<T> {
    public static <T> T defaultValue(){
        return null;
    }
    public T getOrDefault(T value,T defaultValue){
        return (value!=null)? value:defaultValue;
    }

    public static void main(String[] args) {
        final Value<String> value=new Value<>();
        System.out.println(value.getOrDefault(null,Value.defaultValue()));
    }
}
