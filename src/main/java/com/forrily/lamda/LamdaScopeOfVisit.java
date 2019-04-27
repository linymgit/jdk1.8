package com.forrily.lamda;

/**
 * @author linyimin
 * lamda的访问范围
 */
public class LamdaScopeOfVisit {

    static int staticVar;
    int memberVar;

    public static void main(String[] args) {

        staticOrMemberVar();

    }


    // 局部变量可读不可写
    private static void localVariable(){
        int i = 10;
        new Thread(()->{
            // i++; //局部变量再lamda不可写,编译会报错
            System.err.println(i);
        }).start();
    }

    // 常量（本来就不可能修改,所以在lamda中可读）
    private static void constant(){
        final int i = 10;
        new Thread(()->{
            System.err.println(i);
        }).start();
    }

    // lamde可以访问静态变量和成员变量,可读可写
    private static void staticOrMemberVar(){
        new Thread(()->{

            staticVar++;

            LamdaScopeOfVisit demo = new LamdaScopeOfVisit();
            demo.memberVar=123;

            System.err.println(staticVar);
            System.err.println(demo.memberVar);
        }).start();
    }

}
