package com.forrily.func.basic;

@FunctionalInterface
public interface FuncI<F,T> {

  T test(F f);

 // void mustNotExist();
}
