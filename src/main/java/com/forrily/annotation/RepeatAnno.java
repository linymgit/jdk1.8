package com.forrily.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(value = RepeatAnnos.class)
public @interface RepeatAnno {

    String value();
}
