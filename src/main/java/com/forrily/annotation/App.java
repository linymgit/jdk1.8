package com.forrily.annotation;

/**
 * 在dev分支上加入了注释用于测试 。。。
test
 */
public class App {

    public static void main(String[] args) {

    }

    @RepeatAnnos({@RepeatAnno("repeat annotation one"), @RepeatAnno("repeat annotation twor")})
    class RepeatAnnoBeforeJdk8{

    }

    @RepeatAnno("repeat annotation one")
    @RepeatAnno("repeat annotation two")
    class RepeatAnnoOfJdk8{

    }
}
