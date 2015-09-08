package com.twocater.diamond.api.annotation;

import java.lang.annotation.*;

/**
 * Created by chenzhiwei on 15-9-8.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AListener {
    int order() default 0;
}
