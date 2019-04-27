package com.forrily.func.basic;

/**
 * @author linyimin
 * lamda表达式的基本使用和函数的引用（::）
 */
public class App {

    public static void main(String[] args) {
//        FuncI<Integer,Integer> add = a->a+1;
//
//        System.err.println(add.test(1));

        FuncI<String, String> funcI = new FuncRefer()::handler;

        System.err.println(funcI.test("haha"));
    }
}
