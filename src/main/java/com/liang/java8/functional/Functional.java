package com.liang.java8.functional;

/**
 * @author Briliang
 * @date 2014/7/12
 * Description(java 8 函数式接口，可以被隐式转换为lambda表达式
 * 默认方法与静态方法并不影响函数式接口的契约，可以任意使用)
 */
@FunctionalInterface
public interface Functional {
    public void functionalMethod();
    default String defaultMethod(){
        System.out.println("我是默认函数--->");
        return "";
    }
}
