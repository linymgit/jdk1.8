package com.forrily.annotation;

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
