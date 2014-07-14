package com.liang.java8.repeatingannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/7/14.
 * Description:(注解可以用在局部变量，泛型类，父类与接口实现与异常)
 */
public class Annotations {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE,ElementType.TYPE_PARAMETER})
    public @interface NonEmpty{

    }
    public static class Holder<@NonEmpty T> extends @NonEmpty Object{
        public String method() throws @NonEmpty Exception{
            return "Holder Response";
        }
    }
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        final Holder<String> holder=new @NonEmpty Holder<>();
        @NonEmpty Collection<@NonEmpty String> Strings=new @NonEmpty ArrayList<>();
        try {
            System.out.println(holder.method());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
