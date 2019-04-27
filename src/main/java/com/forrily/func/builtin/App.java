package com.forrily.func.builtin;

import com.forrily.func.et1.User;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author linyimin
 * jdk1.8内置的函数式接口
 */
public class App {

    public static void main(String[] args) {

        PredicatesEt.test();
    }


    /**
     * Predicate是一个布尔类型的函数，
     * 该函数只有一个输入参数。
     * Predicate接口包含了多种默认方法，
     * 用于处理复杂的逻辑动词（and, or，negate）
     */
    static class PredicatesEt{
        static void test(){
            Predicate<String> predicate = (s) -> s.length() > 0;

            System.err.println(predicate.test("TEST"));
            System.err.println(predicate.negate().test("Hello world"));

            Predicate<Boolean> nonNull = Objects::nonNull;
            Predicate<Boolean> isNull = Objects::isNull;

            Predicate<String> isEmpty = String::isEmpty;
            Predicate<String> isNotEmpty = isEmpty.negate();

            // ...
        }
    }

    /**
     * Function接口接收一个参数，
     * 并返回单一的结果。
     * 默认方法可以将多个函数串在一起（compse, andThen）
     */
    static class FunctionsEt{
        static void test(){
            Function<String, Integer> toInteger = Integer::valueOf;
            Function<String, String> backToString = toInteger.andThen(String::valueOf);

            backToString.apply("321");     // "321"
        }
    }

    /**
     * Supplier接口产生一个给定类型的结果。
     * 与Function不同的是，Supplier没有输入参数。
     */
    static class SuppliersEt{
        static void test(){
            Supplier<User> userSupplier = User::new;
            userSupplier.get();   // new User
        }
    }


    /**
     * Consumer代表了在一个输入参数上需要进行的操作。
     */
    static class ConsumersEt{
        static void test(){
            Consumer<User> sayhello = (u) -> System.out.println("Hello, "+u.getName());
            sayhello.accept(new User(1, "linym"));
        }
    }


}
