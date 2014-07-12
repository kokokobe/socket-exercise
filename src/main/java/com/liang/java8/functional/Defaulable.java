package com.liang.java8.functional;

import java.util.function.Supplier;

/**
 * @author Briliang
 * @date 2014/7/12
 * Description()
 */
public interface Defaulable {
    public  String notRequired();
    public static Defaulable create(Supplier<Defaulable> supplier){
        return supplier.get();
    }
    static class DefaultableImpl implements Defaulable{
        @Override
        public String notRequired() {
            return "Defaultable implementation";
        }
    }
    static class OveridableImpl implements Defaulable{
        @Override
        public String notRequired() {
            return "Overridden implementation";
        }
    }

    public static void main(String[] args) {
        Defaulable defaulable=Defaulable.create(OveridableImpl::new);
        System.out.println(defaulable.notRequired());
        //构造函数引用不用像以前那样
        defaulable=Defaulable.create(DefaultableImpl::new);
        System.out.println(defaulable.notRequired());
    }
}
