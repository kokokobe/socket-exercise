package com.liang.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Briliang
 * @date 2014/7/12
 * Description()
 */
public class Car {
    public static Car create(final Supplier<Car> supplier){
        return supplier.get();
    }
    public static void collide(final Car car){
        System.out.println("Collided "+car.toString());
    }
    public void follow(final Car another){
        System.out.println("Following the "+another.toString());
    }
    public void repair(){
        System.out.println("Repaired "+this.toString());
    }

    public static void main(String[] args) {
        /**
         * 构造器引用
         */
        final Car car=Car.create(Car::new);
        final List<Car> cars= Arrays.asList(car);
        /**
         * 静态方法引用
         */
        cars.forEach(Car::collide);
        /**
         * 任意对象的方法引用
         */
        cars.forEach(Car::repair);
        /**
         * 特定对象的方法引用
         */
        final Car police=Car.create(Car::new);
        cars.forEach(police::follow);
    }
}
